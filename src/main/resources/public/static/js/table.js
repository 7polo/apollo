function Table(option) {
    var $ele = option.ele;
    var _url = option.url;
    var _columns = option.column || [];
    var _records = option.data || [];
    var _paginate = option.paginate || false;
    var _pagination = {page: 1, size: 10, total: 0};
    var _param = {};
    var _baseparam = option.baseParam || {};
    var _isInit = true;
    var _rowActionMap = {}; // 事件绑定

    this.getColumn = function () {
        return _columns;
    };

    this.getRecord = function (index) {
        return _records[index];
    };

    this.getRecords = function () {
        return _records;
    };

    this.getSelected = function () {

    };

    this.query = function (param) {
        _param = param || {};
        _loadData(1)
    };

    this.refresh = function () {
        _loadData(_pagination.current)
    };

    this.renderer = function () {
        _renderer();
        return this;
    };

    function _buildColumn() {
        var ths = '';
        _columns.forEach(function (column, index) {
            var width = column.width || '';
            var title = column.title || '';
            var align = column.align || '';
            if (column.type === 'check') {
                width = '20px';
                align = column.align || 'center';
                title = ''.concat('<label class="table-checkbox"><input type="checkbox" class="check-all"><span></span></label>')
            }
            // 宽度
            if (width) {
                width = ' width="' + width + '"'
            }
            if (align) {
                align = ' align="' + align + '"'
            }

            ths = ths.concat('<th ' + width + align + ' >').concat(title).concat('</th>')
        });
        return '<thead><tr>'.concat(ths).concat('</tr></thead>')
    }

    function _buildRow() {
        var trs = '';
        _records.forEach(function (record, index) {
            var tds = '';
            _columns.forEach(function (column) {
                var content = record[column.dataIndex] || '';
                if (column.type === 'index') {
                    column.align = "center" || column.align;
                } else if (column.type === 'check') {
                    column.align = "center" || column.align;
                    content = '<label class="table-checkbox"><input type="checkbox" name="ids[]" value="' + index + '"><span></span></label>';
                } else if (column.type === 'action') {
                    var actions = column.actions || [];
                    actions.forEach(function (action, aIndex) {
                        var actionTitle = action.title;
                        if (action.renderer != null) {
                            actionTitle = '<div style="display:inline-block">'.concat(action.renderer(action.title, record)).concat('</div>')
                        } else {
                            if (action.icon) {
                                actionTitle = '<a class="action-icon" href="#!" title="' + action.title + '" data-toggle="tooltip"><i class="' + action.icon + '"></i></a>'
                            }
                        }
                        content = content.concat('<div data-row="' + index + '" data-action="' + aIndex + '" class="btn-group row-action">').concat(actionTitle || '').concat('</div>')
                        if (action.handler) {
                            _rowActionMap[aIndex] = action.handler
                        }
                    })
                } else {
                    if (column.renderer != null) {
                        content = column.renderer(column.title, column.dataIndex, record);
                    }
                }
                // 对齐方式
                var align = (column.align || "").toLowerCase();
                if (align && ["left", "center", "right"].indexOf(align) != -1) {
                    align = ' align="' + align + '"';
                }
                tds = tds.concat('<td ' + align + '>').concat(content).concat('</td>')
            });
            trs = trs.concat('<tr>').concat(tds).concat('</tr>')
        });
        return '<tbody>'.concat(trs).concat('</tbody>')
    }

    function _buildPagination() {
        var total = _pagination.total;
        var current = _pagination.current;
        var size = _pagination.size;
        var totalPage = Math.ceil(total / size);

        var stepTpl = '<li class="page-item {active} {disabled}" data-page="{page}"><a class="page-link" href="#">{text}</a></li>';

        var html = "";

        var steps = [];
        if (total > 0) {
            // 1。上一页
            steps.push({
                disabled: current == 1 ? 'disabled' : '',
                page: current - 1,
                text: '上一页'
            });


            var stepCount = 8;
            var half = Math.ceil((stepCount - 1) / 2);
            var leftIndex = Math.max(1, current - half);
            var rightIndex = Math.min(totalPage, current + half);
            // 补充左边
            if (leftIndex > 1 && rightIndex == totalPage) {
                // 左边位置 = 总量 - 右边偏移量
                leftIndex = Math.max(1, current - (stepCount - (rightIndex - current)));
            }
            // 补充右边
            if (leftIndex == 1 && rightIndex < totalPage) {
                rightIndex = Math.min(totalPage, current + (stepCount - (current - leftIndex)));
            }
            // 2。左边页码
            if (leftIndex != 1) {
                // 第一页
                steps.push({
                    page: 1
                });
                leftIndex++;
                // 省略号
                if (leftIndex != 2) {
                    steps.push({
                        disabled: 'disabled',
                        page: '...'
                    });
                    leftIndex++;
                }
            }
            for (var i = leftIndex; i < current; i++) {
                steps.push({
                    page: i
                });
            }

            // 3。当前页
            steps.push({
                active: "active",
                page: current
            });

            // 4。右边页码
            for (var i = current + 1; i <= rightIndex; i++) {
                if (i == rightIndex && i != totalPage) {
                    steps[steps.length - 1].page = totalPage;
                    if (rightIndex != totalPage - 1) {
                        steps[steps.length - 2] = {
                            page: '...',
                            disabled: 'disabled'
                        }
                    }
                } else {
                    steps.push({
                        page: i
                    })
                }
            }

            // 5。 下一页
            steps.push({
                disabled: (current == totalPage) ? 'disabled' : '',
                page: current + 1,
                text: '下一页'
            });

            for (var i = 0; i < steps.length; i++) {
                steps[i].text = steps[i].text || steps[i].page;
                steps[i].active = steps[i].active || '';
                html += stepTpl.format(steps[i])
            }
        }

        html += '<li class="page-total"><span>共 ' + (total || 0) + ' 条</span></li>';
        return '<ul class="pagination pagination-sm">'.concat(html).concat('</ul>')
    }

    function _loadData(page) {
        _pagination.current = page;
        var param = JSON.parse(JSON.stringify(_param));
        for (var p in _baseparam) {
            if (param[p] == undefined) {
                param[p] = _baseparam[p]
            }
        }
        param.start = _pagination.current;
        param.limit = _pagination.size;

        // 此处采用工具ajax
        utils.ajax({
            url: _url,
            data: param,
            success: function (resp) {
                var data = resp.data;
                _records = data.records;
                _pagination = {
                    total: data.total,
                    current: data.current,
                    size: _pagination.size
                };
                _renderer();
            }
        });
    }

    function _renderer() {
        // 第一次渲染时
        if (_isInit) {
            $ele.html('<div class="table-responsive"><table style="width:100%;table-layout: fixed" class="table table-bordered">'.concat(_buildColumn()).concat(_buildRow()).concat('</table></div>').concat(_buildPagination()));
            _loadData(1);

            // 全选框事件
            $ele.find(".check-all").change(function () {
                $("input[type='checkbox']").prop('checked', $(this).prop("checked"));
            });
            _isInit = false
        } else {
            // 后续为局部渲染
            $ele.find("tbody").replaceWith(_buildRow());
            $ele.find(".pagination").replaceWith(_buildPagination());
            _paginateEvent();

            // 操作列事件注册
            $ele.find(".row-action").click(function () {
                var rowIndex = $(this).attr("data-row");
                var actionIndex = $(this).attr("data-action");
                var action = _rowActionMap[actionIndex];
                if (action) {
                    action.apply(this, [_records[rowIndex], rowIndex])
                }
            })
        }
    }

    function _paginateEvent() {
        // 采用事件委托的方式
        $(".pagination").unbind("click").click(function (e) {
            var step;
            var $target = $(e.target);
            if ($target.hasClass("page-link")) {
                step = $(e.target.parentNode)
            } else if ($target.hasClass("page-item")) {
                step = $target;
            }

            if (step != null) {
                var page = step.attr("data-page");
                if (page && page >= 1 && !step.hasClass("disabled")) {
                    _loadData(page)
                }
            }
        })
    }
}

(function () {
    $.fn.extend({
        datagrid: function (option) {
            option.ele = $(this);
            return new Table(option).renderer();
        }
    })
})();
