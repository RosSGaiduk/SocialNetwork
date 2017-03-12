(function($) {
	$(document).ready(function() {
    like();
    select();
		menu();
		menuOpen();
		serachOpen();
	});

  function like(){
    var like = $(".like-icon")
        likeCounter = parseInt($(".likes-text").text());
    $(like).click(function (){
      if($(this).hasClass("no-liked")){
        $(".likes-text").text(++likeCounter);
        $(this).removeClass("no-liked");
      }
      else {
        $(".likes-text").text(--likeCounter);
        $(this).addClass("no-liked");
      }

    });
  }

  function select(){
    var select = $(".input-with-icon .dropdown-menu li");
    $(select).click(function(){
      var selectedLI = $(this).text();
      $(".select-btn .content-here").text(selectedLI);
    });
  }

	function menu(){
		var container = $(".container").width(),
				htmlVal = $(document).width(),
				min = (htmlVal - container) / 2,
				t = -350 - min;
		$('.hidden-menu').css('left', t);
	}

	function menuOpen(){
		var removeClass = true;
		$(".open-btn").click(function () {
		    $(".hidden-menu").toggleClass('opened');
		    removeClass = false;
				if($(".hidden-menu").hasClass('opened')){
					$("body").addClass("body-opacity");
				}
				else{
					$("body").removeClass("body-opacity");
				}
		});

		$(".hidden-menu").click(function() {
		    removeClass = false;
		});

		$("html").click(function () {
		    if (removeClass) {
		        $(".hidden-menu").removeClass('opened');
		    }
				if($(".hidden-menu").hasClass('opened')){
					$("body").addClass("body-opacity");
				}
				else{
					$("body").removeClass("body-opacity");
				}
		    removeClass = true;
		});
	}

	function serachOpen(){
		$(".search-btn").click(function () {
			$(".select").toggleClass("search-open");
			$(".search-input-row").toggleClass("search-open");
		});
	}
  // Controller
	$(document).on("click", "#show-more", function() {
			var data_id = '#' + $(this).attr("data-attr");
			$(data_id).fadeToggle("hidden-content");
	});

	$(document).on("click", "#show-more2", function() {
			var data_id = '#' + $(this).attr("data-attr");
			$(data_id).fadeToggle("hidden-content");
	});

	$(document).on("click", "#show-more", function() {
		$(".show").toggleClass("height-auto");
		});

	$(".first-name").keyup(function() {
		stringObj.input = $(this);
		validation.string(stringObj);
	});

	$(".last-name").keyup(function() {
		stringObj.input = $(this);
		stringObj.goodmessage = "test";
		validation.string(stringObj);
	});

	$(".user-name").keyup(function() {
		stringObj.input = $(this);
		stringObj.goodmessage = "test";
		validation.string(stringObj);
	});

	$(".date1").keyup(function() {
		stringObj.input = $(this);
		stringObj.goodmessage = "test123";
		validation.number(stringObj);
	});
	$(".date2").keyup(function() {
		stringObj.input = $(this);
		stringObj.goodmessage = "test123";
		validation.number(stringObj);
	});
	$(".date3").keyup(function() {
		stringObj.input = $(this);
		stringObj.goodmessage = "test123";
		validation.number(stringObj);
	});

	$(".email").keyup(function() {
		stringObj.input = $(this);
		stringObj.goodmessage = "test";
		validation.email(stringObj);
	});

	$(".pass").keyup(function() {
		stringObj.input = $(this);
		stringObj.goodmessage = "test";
		validation.pass(stringObj);
	});

	$(".pass-confirm").keyup(function() {
		stringObj.input = $(this);
		stringObj.goodmessage = "test";
		validation.passConfirm(stringObj);
	});
	$(".company-name").keyup(function() {
		stringObj.input = $(this);
		stringObj.goodmessage = "test";
		validation.string(stringObj);
	});
	$(".country").keyup(function() {
		stringObj.input = $(this);
		stringObj.goodmessage = "test";
		validation.string(stringObj);
	});
	$(".state").keyup(function() {
		stringObj.input = $(this);
		stringObj.goodmessage = "test";
		validation.string(stringObj);
	});
	$(".city").keyup(function() {
		stringObj.input = $(this);
		stringObj.goodmessage = "test";
		validation.string(stringObj);
	});

	var validation = {

		string: function(stringObj) {
				var checkend = /^[a-zA-Z]*$/, // string RegExp
						value = stringObj.input.val(), //value of our input
						ok = checkend.test(value), //test out RegExp
						inputLength = (value).length, //input length
						praParent = stringObj.input.parent().parent();
						if (ok && inputLength >= 2) {
							praParent.find(".alert").remove();
							praParent.append("<span class='alert'>"+ stringObj.goodmessage + "</span>");
						}
						else {
							praParent.find(".alert").remove();
							praParent.append("<span class='alert'>"+ stringObj.badmessage + "</span>");
						}
			},
			number : function(stringObj){
				var checknumber = /^\d+$/,
						value = stringObj.input.val(),
						ok = checknumber.test(value),
						inputLength = (value).length, //input length
						praParent = stringObj.input.parent().parent();

						if (ok && inputLength >= 2) {
							praParent.find(".alert").remove();
							praParent.append("<span class='alert'>"+ stringObj.goodmessage + "</span>");
						}
						else {
							praParent.find(".alert").remove();
							praParent.append("<span class='alert'>"+ stringObj.badmessage + "</span>");
						}
			},
			email : function(stringObj){
				var checkemail = /^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i,
						value = stringObj.input.val(),
						ok = checkemail.test(value),
						inputLength = (value).length, //input length
						praParent = stringObj.input.parent().parent();

						if (ok && inputLength >= 2) {
							praParent.find(".alert").remove();
							praParent.append("<span class='alert'>"+ stringObj.goodmessage + "</span>");
						}
						else {
							praParent.find(".alert").remove();
							praParent.append("<span class='alert'>"+ stringObj.badmessage + "</span>");
						}
			},
			pass : function(stringObj){
				var checkpass = (/^(?=.*\d)[a-zA-Z0-9]{6,}$/),
						value = stringObj.input.val(),
						ok = checkpass.test(value),
						inputLength = (value).length, //input length
						praParent = stringObj.input.parent().parent();

						if (ok && inputLength >= 6) {
							praParent.find(".alert").remove();
							praParent.append("<span class='alert'>"+ stringObj.goodmessage + "</span>");
						}
						else {
							praParent.find(".alert").remove();
							praParent.append("<span class='alert'>"+ stringObj.badmessage + "</span>");
						}
			},
			passConfirm : function(stringObj){
				var praParent = stringObj.input.parent().parent(),
				    pass = $(".pass").val(),
						value = stringObj.input.val();

						if (pass == value){
							praParent.find(".alert").remove();
							praParent.append("<span class='alert'>"+ stringObj.goodmessage + "</span>");
						}
						else {
							praParent.find(".alert").remove();
							praParent.append("<span class='alert'>"+ stringObj.badmessage + "</span>");
						}
			}
	}

	var stringObj = {
		input: "",
		goodmessage: "zaebis name",
		badmessage: "huinya name"
	}




}(jQuery));
