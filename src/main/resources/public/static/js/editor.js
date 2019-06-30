var dirTree;
var TYPES = {
    note: {
        name: 'note',
        saveUrl: '/note/save',
        deleteUrl: '/note/delete',
        order: 2
    },
    catalog: {
        name: 'catalog',
        saveUrl: '/catalog/save',
        deleteUrl: '/catalog/delete',
        order: 1
    }
};

function initDirTree() {

    var treeInst = $('#dirTree').jstree({
        'core': {
            "check_callback": true,
            'data': function (obj, callback) {
                var data = {
                    id: "root",
                    type: TYPES.catalog.name,
                    state: {
                        'opened': true
                    },
                    text: "我的文档"
                };
                callback.call(this, data);
            }
        },
        "plugins": ["dnd", "contextmenu", "themes", "types", "wholerow", 'sort', 'unique', 'search'],
        'types': {
            "#": {
                "max_children": 1, // 子目录个数
                "valid_children": ["catalog"]
            },
            'catalog': {
                'icon': "type-icon dir-icon",
                "valid_children": ["catalog", "note"]
            },
            'note': {
                'icon': "type-icon file-icon",
                "valid_children": []
            }
        },
        "themes": {
            "theme": "default",
            "dots": false,
            "icons": true
        },
        'sort': treeFunc.sort,
        "contextmenu": {
            select_node: false,
            show_at_node: true,
            items: function (o, cb) {
                var actions = {};
                if (o.type == TYPES.catalog.name && dirTree._mode != TYPES.note.name) {
                    actions.create = {
                        "label": "新增",
                        "action": function (data) {
                            var node = dirTree.get_node(data.reference);
                            dirTree.create_node(node, {type: TYPES.catalog.name}, "last", function (new_node) {
                                setTimeout(function () {
                                    new_node.a_attr.isAdd = true;
                                    dirTree.edit(new_node);
                                }, 0);
                            });
                        }
                    }
                    actions.createNote = {
                        "label": "新增文档",
                        "action": function (data) {
                            var node = dirTree.get_node(data.reference);
                            dirTree.create_node(node, {type: TYPES.note.name}, "last", function (new_node) {
                                setTimeout(function () {
                                    new_node.a_attr.isAdd = true;
                                    dirTree.edit(new_node);
                                }, 0);
                            });
                        }
                    }
                }

                // 根节点不显示重命名和删除
                if (o.parent !== "#") {
                    actions.rename = {
                        "label": "重命名",
                        "action": function (data) {
                            var node = dirTree.get_node(data.reference);
                            dirTree.edit(node);
                        }
                    };
                    actions.del = {
                        "label": "删除",
                        "action": function (data) {
                            treeFunc.del(dirTree.get_node(data.reference))
                        }
                    }
                }
                return actions;
            }
        }
    });

    treeInst.on("select_node.jstree", function (event, data) {
        var node = data.node;
        if (treeFunc.needIgnorEvent(node)) {
            return;
        }
        treeFunc.select(event, data)
    });

    treeInst.on("after_open.jstree", function (event, data) {
        var node = data.node;
        if (treeFunc.needIgnorEvent(node)) {
            return;
        }
        treeFunc.loadChildNodes(data.node);
    });

    treeInst.on("rename_node.jstree", function (event, data) {
        var node = data.node;
        if (treeFunc.needIgnorEvent(node)) {
            return;
        }
        treeFunc.updateNode(data)
    });

    treeInst.on("move_node.jstree", function (event, data) {
        var node = data.node;
        if (treeFunc.needIgnorEvent(node)) {
            return;
        }
        treeFunc.updateNode(data)
    });

    dirTree = $('#dirTree').jstree();
}

var treeFunc = {

    // 忽略事件
    ignoreEvent: function (node) {
        node.a_attr.ignoreEvent = true;
    },

    // 是否需要忽略事件
    needIgnorEvent: function (node) {
        if (node.a_attr.ignoreEvent) {
            delete node.a_attr.ignoreEvent;
            return true
        }
        return false;
    },


    // 排序
    sort: function (a, b) {
        var nodeA = this.get_node(a);
        var nodeB = this.get_node(b);
        if (nodeA.type == nodeB.type) {
            return nodeA.text.charAt(0) > nodeB.text.charAt(0)
        } else {
            return TYPES[nodeA.type].order > TYPES[nodeB.type].order
        }
    },

    initLoad: function (noteId) {
        if (noteId) {
            treeFunc.loadNoteToEdit(noteId)
        } else {
            treeFunc.loadAllCatalogNodes()
        }
    },

    // 加载全部目录节点
    loadAllCatalogNodes: function (callback) {
        utils.ajax({
            url: 'catalog/loadTreeAll',
            success: function (resp) {
                var root = dirTree.get_node("root");
                resp.data.forEach(function (node) {
                    dirTree.create_node(root, node, "last")
                });
                if (callback) {
                    callback()
                }
            }
        });
    },

    //其他页面编辑是进入的接口
    loadNoteToEdit: function (noteId) {
        treeFunc.loadAllCatalogNodes(function () {
            utils.ajax({
                url: 'note/queryCatalogDto/' + noteId,
                success: function (resp) {
                    var pNode = dirTree.get_node(resp.data.dirId);
                    treeFunc.loadChildNodes(pNode, function () {
                        treeFunc.selectNodeWithoutEvent(pNode);
                        dirTree.select_node({id: noteId})
                    });
                }
            });
        })
    },

    selectNodeWithoutEvent: function (node) {
        treeFunc.ignoreEvent(node);
        if (node.id == "root") {
            dirTree.open_node(node);
            return
        }
        var pNode = dirTree.get_node(dirTree.get_parent(node));
        treeFunc.selectNodeWithoutEvent(pNode);
        dirTree.open_node(node)
    },

    // 点击目录时，请求当前子节点数据， 智能更新
    loadChildNodes: function (dir, callback) {
        utils.ajax({
            url: 'catalog/loadChildNode/' + dir.id,
            success: function (resp) {
                var nodes = resp.data;
                if (nodes && nodes.length > 0) {
                    nodes.forEach(function (node) {
                        // todo 暂时只加载 note
                        if (node.type == TYPES.note.name) {
                            dirTree.create_node(dir, node, "last")
                        }
                    });
                    treeFunc.ignoreEvent(dir);
                    dirTree.open_node(dir);
                    if (callback) {
                        callback()
                    }
                }
            }
        });
    },

    // 树选择事件
    select: function (event, data) {
        var node = data.node;
        if (TYPES.note.name == node.type) {
            editNote(node.id);
            return
        }
        $("#noteForm").hide();
        if (TYPES.catalog.name == node.type) {
            treeFunc.loadChildNodes(node);
        }
    },

    // 删除节点
    del: function (node) {
        utils.post({
            url: TYPES[node.type].deleteUrl + '/' + node.id,
            success: function () {
                dirTree.delete_node(node)
            }
        });
    },

    updateNode: function (data) {
        var node = data.node;
        var param = {
            uid: node.id.indexOf("j") == 0 ? null : node.id,
            dirId: node.parent,
            name: node.text
        };
        utils.post({
            url: TYPES[node.type].saveUrl,
            data: param,
            success: function (resp) {
                var result = resp.data;
                if (result.uid !== node.id) {
                    dirTree.set_id(node, result.uid)
                }
                if (node.type == TYPES.note.name) {
                    // 更新 title
                    $("#noteName").val(node.text)
                }
            },
            error: function () {
                treeFunc.ignoreEvent(node);
                if (node.a_attr.isAdd) {
                    // 新建还原
                    dirTree.delete_node(node)
                } else if (data.old_parent && data.parent != data.old_parent) {
                    // 移动还原
                    var oldParent = dirTree.get_node(data.old_parent);
                    dirTree.move_node(node, oldParent)
                } else {
                    // 修改名称还原
                    dirTree.set_text(node, node.original.text)
                }
            }
        });
    }
};

// 编辑note
function editNote(noteId) {
    $("#md-html").empty();
    var form = $("#noteForm");
    form.show();
    utils.ajax({
        url: '/note/' + noteId,
        success: function (resp) {
            utils.fillForm(form, resp.data);
            $("#md").trigger("blur");

            var tagValues = [];
            var tags = resp.data.tags;
            if (tags && tags.length > 0) {
                for (var i = 0; i < tags.length; i++) {
                    if (tags[i].name) {
                        tagValues.push(tags[i].name);
                    }
                }
            }

            // 标签
            $("#tag").tag({
                name: "tagNames",
                placeholder: '请输入标签',
                inputclass: 'form-control tag-input',
                clear: true,
                value: tagValues.join(",")
            });
        }
    })
}

// 编辑器初始化
function markdownInit() {
    var renderer = new marked.Renderer();
    renderer.blockquote = function (html) {
        return html;
    };
    marked.setOptions({
        renderer: renderer,
        gfm: true,
        tables: true,
        breaks: false,
        pedantic: false,
        sanitize: false,
        smartLists: true,
        smartypants: false,
        preClass: 'hljs',
        highlight: function (code, lang, callback) {
            if (lang && hljs.getLanguage(lang)) {
                return hljs.highlight(lang, code, true).value;
            } else {
                return hljs.highlightAuto(code).value;
            }
        }
    });

    $("#md").on("keydown blur", function (e) {
        if (e.keyCode === 9) {
            e.preventDefault();
            var indent = '    ';
            var start = this.selectionStart;
            var end = this.selectionEnd;
            var selected = window.getSelection().toString();
            selected = indent + selected.replace(/\n/g, '\n' + indent);
            this.value = this.value.substring(0, start) + selected + this.value.substring(end);
            this.setSelectionRange(start + indent.length, start + selected.length);
        }
        renderMd()
    });
}

function renderMd() {
    $('.md-html').html(marked($("#md").val()))
}

// 分享按钮
function shareBtnInit() {
    $(".shareBlogBtn").click(function () {
        var _this = $(this);
        if (_this.attr("disabled")) {
            return
        }
        utils.post({
            url: '/note/share/' + utils.formData($("#noteForm")).uid,
            success: function () {
                _this.removeAttr("disabled");
                utils.notifySuccess("分享成功")
            },
            error: function (e) {
                _this.removeAttr("disabled");
                utils.notifySuccess(e.msg)
            }
        });
    });
}

// 查看按钮
function viewBlogBtnInit() {
    $(".viewBlogBtn").click(function () {

        var note = utils.formData($("#noteForm"));
        if (note.uid && note.publishDt) {
            window.open('/blog/' + note.uid + '.html')
        } else {
            utils.notifyError("该笔记未分享，无法查看")
        }
    });
}

// 保存按钮
function saveBtnInit() {
    // 注册按钮事件
    $(".saveBtn").click(function () {
        var _this = $(this);
        if (_this.attr("disabled")) {
            return
        }
        _this.attr("disabled", true);
        utils.post({
            url: TYPES.note.saveUrl,
            data: utils.formData($("#noteForm")),
            success: function () {
                _this.removeAttr("disabled");
                utils.notifySuccess("保存成功")
            },
            error: function () {
                _this.removeAttr("disabled");
            }
        });
    });
}

// 模式按钮
function modeBtnInit() {
    var mode = [{
        type: 'readAndWrite',
        name: '读写',
        clz: 'fa fa-columns'
    }, {
        type: 'fullRead',
        name: '阅读',
        clz: 'fa fa-eye'
    }, {
        type: 'fullWrite',
        name: '编辑',
        clz: 'fa fa-pencil'
    }];
    var currMode = mode[0];
    // 初始状态
    $(".modeBtn").click(function () {
        var type = $(this).attr("data-mode");
        if (!type) {
            type = currMode
        }
        mode.forEach(function (it, index) {
            if (it.type == type) {
                currMode = mode[(index + 1) % mode.length];
                return true
            }
        });
        $(this).attr("data-mode", currMode.type).attr("data-original-title", currMode.name).html('<i class="' + currMode.clz + '"></i>');
        $(".content-area").removeClass([mode[0].type, mode[1].type, mode[2].type])
            .addClass(currMode.type);
    }).trigger("click")
}

// 查询
function searchInit() {
    var to = false;
    $('#search').keyup(function () {
        if (to) {
            clearTimeout(to);
        }
        to = setTimeout(function () {
            var v = $('#search').val();
            dirTree._search = true;
            dirTree.search(v);
        }, 250);
    });
}

// seo 设置
function seoInit() {
    $(".seoBtn").click(function () {
        var noteId = utils.formData($("#noteForm")).uid;
        if (noteId != null) {
            utils.fillForm($("#seo-form"), {type: 'blog', relateId: noteId});
            $('#seoModal').unbind('show.bs.modal').on('show.bs.modal', function (e) {
                utils.ajax({
                    url: 'seo/blog/' + noteId,
                    success: function (resp) {
                        if (resp.data) {
                            utils.fillForm($("#seo-form"), resp.data)
                        }
                    }
                })
            }).modal('toggle');
        }
    });

    $(".seoSaveBtn").click(function () {
        utils.post({
            url: 'seo/save',
            data: utils.formData($("#seo-form")),
            success: function () {
                $('#seoModal').modal("hide");
                utils.notifySuccess("保存成功")
            }
        })
    })
}

$(function () {
    initDirTree();
    markdownInit();
    // 当note的name发生变化响应tree中的note
    $("#noteName").on("keyup blur", function () {
        var node = dirTree.get_node(dirTree.get_selected()[0]);
        treeFunc.ignoreEvent(node);
        dirTree.rename_node(node, $(this).val())
    });

    // 按钮事件
    saveBtnInit();
    shareBtnInit();
    modeBtnInit();
    viewBlogBtnInit();

    searchInit();
    seoInit();

    renderMd = debounce(600, renderMd)

});
