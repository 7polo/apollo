var Tag = function (op) {
    var $ele = op.ele;
    var $vIn = null;
    var $in = null;
    var _clear = op.clear!=false;
    var _value = [];
    this.getValue = function () {
        return _value;
    }
    function renderer() {
        $ele.addClass("tag").empty();
        $ele.append('<div style="display: inline-block" class="labels"><input class="in" placeholder='+(op.placeholder||"")+'/></div>');
        $in = $ele.find(".in");
        if (op.name) {
            $ele.append('<input type="hidden" name="' + op.name + '">')
            $vIn = $ele.children("input[name='" + op.name + "']")
        }
        $in.keydown(function (event) {
            if (event.keyCode == 32 || event.keyCode == 13) {
                addTag($(this).val())
                $(this).val("");
            }
        })
        _value = [];
        (op.value || "").split(",").forEach(function(item){
            addTag(item)
        });
    }

    function addTag(tag) {
        if (tag && _value.indexOf(tag) == -1) {
            _value.push(tag)
            var delDom = _clear ?'<a href="#" title="Removing tag">x</a>':''
            var label = '<span class="tag"><span>' + tag + '</span>' + delDom + '</span>'
            $in.before(label);
            $in.siblings(".tag").children("a").unbind("click").on("click", function () {
                var index = _value.indexOf($(this).prev().text());
                if (index != -1) {
                    _value.splice(index, 1);
                    $(this).parent().remove();
                    if ($vIn) {
                        $vIn.val(_value.join(","))
                    }
                }
            });
            if ($vIn) {
                $vIn.val(_value.join(","))
            }
        }
    }
    renderer();
};

(function () {
    $.fn.extend({
        tag: function (op) {
            op = op || {};
            op.ele = $(this);
            return new Tag(op);
        }
    })
})();
