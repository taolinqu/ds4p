//Code to execute on document.ready event for index.html page
$(document).ready(function() {
			$("body").removeClass('fouc_loading');
			
			$("#loginForm").validate({
				onfocusout: false,
			    onkeyup: false,
			    onclick: false,
				showErrors: function(errorMap, errorList) {
		            $(".errormsg").html($.map(errorList, function (el) {
		            	$('.loginError').empty();
		                return el.message;
		            }).join("<br/> "));
		        },
		        wrapper: "span",
				rules: {
					j_username: {
						required: true,
						minlength: 3,
	                    maxlength: 30
					} ,
					j_password: {
						required: true,
						minlength: 8,
	                    maxlength: 30
					}			
				},
				messages: {
					j_username: {
						required: "Username is required",
						minlength: $.format("Username must be at least {0} characters long"),
	                    maxlength: $.format("Username must less than {0} characters long")
					} ,
					j_password: {
						required: "Password is required",
						minlength: $.format("Password must be at least {0} characters long"),
	                    maxlength: $.format("Password must less than {0} characters long")
					} 
				}
			});
		}
);