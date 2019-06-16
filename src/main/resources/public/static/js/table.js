function Table(option) {
    var $ele = option.ele;
    var _url = option.url;
    var _columns = option.column || [];
    var _records = option.data || [];
    var _paginate = option.paginate || false;
    var _pagination = {page: 1, size: 10, total: 0};
    var _param = {};
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
                        if (action.handle) {
                            _rowActionMap[aIndex] = action.handle
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
        var page = _pagination.current;
        var size = _pagination.size;
        var totalPage = Math.ceil(total / size);
        var html = "";
        html += '<li class="page-item {disabled}" data-page="{page}"><a class="page-link" href="#" tabindex="-1">上一页</a></li>'.format({
            disabled: page == 1 ? 'disabled' : '',
            page: page - 1
        });
        for (var i = page - 5; i < page + 5 && i <= totalPage; i++) {
            if (i <= 0) {
                continue
            }
            html += '<li class="page-item {active}" data-page="{page}"><a class="page-link" href="#">{page}</a></li>'.format({
                active: i == page ? "active" : "",
                page: i
            })
        }
        html += '<li class="page-item {disabled}" data-page="{page}"><a class="page-link" href="#">下一页</a></li>'.format({
            disabled: (page == totalPage) ? 'disabled' : '',
            page: page + 1
        });
        return '<ul class="pagination">'.concat(html).concat('</ul>')
    }

    function _loadData(page) {
        _pagination.current = page;
        var param = JSON.parse(JSON.stringify(_param));
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
        $(".page-item").unbind("click").click(function () {
            var page = $(this).attr("data-page");
            if (page && page >= 1 && !$(this).hasClass("disabled")) {
                _loadData(page)
            }
        })
    }
}

(function () {
    $.fn.extend({
        datagrid: function (option) {
            option.ele = $(this);
            return new Table(option);
        }
    })
})();
