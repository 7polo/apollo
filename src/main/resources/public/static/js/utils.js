var utils = function () {

    /**
     * 页面loading
     */
    var pageLoader = function ($mode) {
        var $loadingEl = jQuery('#lyear-loading');
        $mode = $mode || 'show';
        if ($mode === 'show') {
            if ($loadingEl.length) {
                $loadingEl.fadeIn(250);
            } else {
                jQuery('body').prepend('<div id="lyear-loading"><div class="spinner-border text-primary" role="status"><span class="sr-only">Loading...</span></div></div>');
            }
        } else if ($mode === 'hide') {
            if ($loadingEl.length) {
                $loadingEl.fadeOut(250);
            }
        }
        return false;
    };

    /**
     * 页面小提示
     * @param $msg 提示信息
     * @param $type 提示类型:'info', 'success', 'warning', 'danger'
     * @param $icon 图标，例如：'fa fa-user' 或 'glyphicon glyphicon-warning-sign'
     * @param $from 'top' 或 'bottom'
     * @param $align 'left', 'right', 'center'
     * @author CaiWeiMing <314013107@qq.com>
     */
    var tips = function ($msg, $type, $icon, $from, $align) {
        $type = $type || 'info';
        $from = $from || 'top';
        $align = $align || 'center';
        $enter = $type == 'danger' ? 'animated shake' : 'animated fadeInUp';

        jQuery.notify({
                icon: $icon,
                message: $msg
            },
            {
                element: 'body',
                type: $type,
                allow_dismiss: true,
                newest_on_top: true,
                showProgressbar: false,
                placement: {
                    from: $from,
                    align: $align
                },
                offset: 20,
                spacing: 10,
                z_index: 10800,
                delay: 3000,
                timer: 1000,
                animate: {
                    enter: $enter,
                    exit: 'animated fadeOutDown'
                }
            });
    };


    /**
     *
     * @param op
     * {
     *     url, param, success, fail, type
     * }
     */
    var request = function (op) {
        if (op.loading) {
            utils.loading('show');
        }
        jQuery.ajax({
            headers: {
                "token": 'aaaaaa'//此处放置请求到的用户token
            },
            type: op.type || "GET",
            url: op.url,
            data: op.data,
            success: function (data) {
                if (op.loading) {
                    utils.loading('hide');
                }
                if (data.code == "ERROR") {
                    return this.error(data)
                }
                if (typeof op.success === "function") {
                    op.success(data)
                }
            },
            error: function (error) {
                if (op.loading) {
                    utils.loading('hide');
                }
                if (typeof op.error === "function") {
                    return op.error(error)
                }
                utils.notify('服务器错误，请稍后再试~', 'error');
            }
        })
    };

    var getFormData = function ($form) {
        var t = $form.serializeArray();
        var data = {}
        $.each(t, function () {
            data[this.name] = this.value;
        });
        return data;
    };

    var fillFormData = function ($form, data) {
        $form.find('input[type=text],select,textarea,input[type=hidden]').each(function () {
            $(this).val(null);
        });
        console.log(utils.formData($form));
        $.each(data, function (index, item) {
            $form.find("[name=" + index + "]").val(item);
        });
    };

    var _escape2Html = function (str) {
        var arrEntities = {'lt': '<', 'gt': '>', 'nbsp': ' ', 'amp': '&', 'quot': '"'};
        return str.replace(/&(lt|gt|nbsp|amp|quot);/ig, function (all, t) {
            return arrEntities[t];
        });
    };

    return {
        // 页面小提示
        notify: function ($msg, $type, $icon, $from, $align) {
            tips($msg, $type, $icon, $from, $align);
        },
        notifySuccess: function (msg) {
            utils.notify(msg, 'success');
        },
        notifyError: function (msg) {
            utils.notify(msg, 'error');
        },
        format: function (dict) {
            return this.replace(/\{(\w+)\}/g, function (s, i) {
                return dict[i] || "";
            });
        },

        // 页面加载动画
        loading: function ($mode) {
            pageLoader($mode);
        },
        // ajax 封装
        ajax: function (op) {
            request(op)
        },
        // ajax 封装
        post: function (op) {
            op.type = "post";
            request(op)
        },

        formData: function (formEle) {
            return getFormData(formEle)
        },
        fillForm: function (formEle, data) {
            fillFormData(formEle, data)
        },

        escape2Html: function (str) {
            return _escape2Html(str)
        }
    };
}();
String.prototype.format = utils.format;


