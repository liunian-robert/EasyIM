
$(function() {
	validateKickout();
    validateLoginRule();
    validateRegisterRule();
	$('.imgcode').click(function() {
		var url = ctx + "captcha/captchaImage?type=" + captchaType + "&s=" + Math.random();
		$(".imgcode").attr("src", url);
	});
});

$.validator.setDefaults({
    submitHandler: function(form) {
        if(form.name == "login") {
            login();
        }
        if (form.name == "register") {
            register();
        }
    }
});

function login() {
	$.modal.loading($("#btnSubmit").data("loading"));
	var username = $.common.trim($("input[name='username']").val());
    var password = $.common.trim($("input[name='password']").val());
    var validateCode = $("input[name='validateCode']").val();
    var rememberMe = $("input[name='rememberme']").is(':checked');
    $.ajax({
        type: "post",
        url: ctx + "login",
        data: {
            "username": username,
            "password": password,
            "validateCode" : validateCode,
            "rememberMe": rememberMe
        },
        success: function(r) {
            if (r.code == 0) {
                location.href = ctx + 'index';
            } else {
            	$.modal.closeLoading();
            	$('.imgcode').click();
            	$(".code").val("");
            	$.modal.msg(r.msg);
            }
        }
    });
}

function register(){
    $.modal.loading($("#registerSubmit").data("loading"));
    var username = $.common.trim($("input[name='rusername']").val());
    var phone = $.common.trim($("input[name='phone']").val());
    var password = $.common.trim($("input[name='rpassword']").val());
    var email = $("input[name='remail']").val();
    $.ajax({
        type: "post",
        url: ctx + "register",
        data: {
            "username": username,
            "phone":phone,
            "password": password,
            "email" : email
        },
        success: function(r) {
            alert(r);
            if (r.code == 0) {
                alert("success");
                location.href = ctx + 'registerSuccess';
            } else {
                $.modal.closeLoading();
                $.modal.msg(r.msg);
            }
        }
    });
}

function validateLoginRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signinForm").validate({
        rules: {
            username: {
                required: true
            },
            password: {
                required: true
            }
        },
        messages: {
            username: {
                required: icon + "请输入您的用户名",
            },
            password: {
                required: icon + "请输入您的密码",
            }
        }
    })
}

jQuery.validator.addMethod("isMobile", function(value, element) {
    var length = value.length;
    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "请正确填写手机号码");

function validateRegisterRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            rusername: {
                required: true
            },
            phone: {
                required: true,
                minlength:11,
                isMobile:true,
            },
            remail: {
                required: true
            },
            rpassword: {
                required: true
            }
        },
        messages: {
            rusername: {
                required: icon + "请输入您的用户名",
            },
            phone: {
                required: icon + "请输入您的手机号",
                minlength:"请输入11位数字",
                isMobile:"请输入有效的手机号"
            },
            remail: {
                required: icon + "请输入您的邮箱",
            },
            rpassword: {
                required: icon + "请输入您的密码",
            }
        }
    })
}
function validateKickout() {
	if (getParam("kickout") == 1) {
	    layer.alert("<font color='red'>您已在别处登录，请您修改密码或重新登录</font>", {
	        icon: 0,
	        title: "系统提示"
	    },
	    function(index) {
	        //关闭弹窗
	        layer.close(index);
	        if (top != self) {
	            top.location = self.location;
	        } else {
	            var url  =  location.search;
	            if (url) {
	                var oldUrl  = window.location.href;
	                var newUrl  = oldUrl.substring(0,  oldUrl.indexOf('?'));
	                self.location  = newUrl;
	            }
	        }
	    });
	}
}

function getParam(paramName) {
    var reg = new RegExp("(^|&)" + paramName + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}