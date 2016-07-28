
	//显示修改用户密码
	function editUserPassword(){
		jQuery('#editPasswordForm').validationEngine('hide');//隐藏验证弹窗
		$("#editPasswordForm :input").each(function () {
			$(this).val("");
		});
		$("#avatar_b").empty();

		$("#editPassword").modal('show').on('hide.bs.modal', function() {
			$('#editPasswordForm').bootstrapValidator('resetForm',true);
		});
	}
	/*取消弹层方法*/
	function closeUserPassword(){
		jQuery('#editPasswordForm').validationEngine('hide');//隐藏验证弹窗
		$("#editPasswordForm :input").each(function () {
			$(this).val("");
		});
		$("#avatar_b").empty();
		$("#editPassword").modal('hide');
	}

	/*修改用户密码*/
	function saveUserPassword(){
		$('#editPasswordForm').data('bootstrapValidator').validate();
		if(!$('#editPasswordForm').data('bootstrapValidator').isValid()){
			return ;
		}

		var userNewPassword = $("#userNewPassword").val();
		$.ajax({
			url:"../web/permi/user/update/password",
			data:{password:userNewPassword},
			async:false,
			type: "POST",
			success: function(data){
				closeUserPassword();
				if(data == "1"){
					bootbox.alert("密码修改成功!");
				}else{
					bootbox.alert("密码修改失败!");
				}
			}, error: function (XMLHttpRequest, textStatus, errorThrown) {
				bootbox.alert("操作失败!")//保存成功弹窗
			}
		})
	}

	/**
	 * 修改密码验证
	 */
	$('#editPasswordForm').bootstrapValidator({
		message: 'This value is not valid',
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},fields: {
			userOldPassword: {
				validators: {
					notEmpty: {
						message: '原始密码不能为空'
					},
					regexp: {
						regexp: '^[0-9]+$',
						message: '密码只能包含数字'
					},
					stringLength: {
						min: 6,
						max: 6,
						message: '密码不能小于6个数字，不能大于10个字符'
					},
					remote: {
						url: '../web/permi/user/info/isPassword',
						type: "post",
						async: false,
						data: function (validator, $field, value) {
							return {
								password: $("#userOldPassword").val()
							};
						},
						message: '原始密码错误'
					}
				}
			},
			userNewPassword: {
				validators: {
					notEmpty: {
						message: '新密码不能为空'
					},
					regexp: {
						regexp: '^[0-9]+$',
						message: '密码只能包含数字'
					},
					stringLength: {
						min: 6,
						max: 6,
						message: '密码不能小于6个数字，不能大于10个字符'
					}
				}
			},
			userRePassword: {
				validators: {
					notEmpty: {
						message: '确认密码不能为空'
					},
					regexp: {
						regexp: '^[0-9]+$',
						message: '密码只能包含数字'
					},
					stringLength: {
						min: 6,
						max: 6,
						message: '密码不能小于6个数字，不能大于10个字符'
					},
					callback: {
						message: '确认密码不一致',
						callback: function (value, validator, $field) {
							if ($("[name=userNewPassword]").val() != value) {
								return false;
							}
							return true;
						}
					}
				}
			}
		}
	});


