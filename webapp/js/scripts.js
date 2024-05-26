String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};


$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();
    var queryString = $("form[name=answer]").serialize();

    $.ajax({
        type: "post",
        url: "/api/qna/addAnswer",
        data: queryString,
        dataType: "json",
        success: onSuccess,
        error: onError
    });
};

function onSuccess(data, status) {
    let answer = data.answer;
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(answer.writer, new Date(answer.writer), answer.contents, answer.answerId);
    $(".qna-comment-slipp-articles").prepend(template);
    $("textarea[name=contents]").val("");
}

function onError(data, status) {
    console.log(data);
}

$(".qna-comment-slipp-articles").on("click", ".form-delete", deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();
    var deleteBtn = $(this);
    var queryString = deleteBtn.closest("form").serialize();

    $.ajax({
        type: "post",
        url: "/api/qna/deleteAnswer",
        data: queryString,
        dataType: "json",
        success: function (data, status) {
            if (data.status == true) {
                deleteBtn.closest("article").remove();
            } else {
                alert(data.errorMessage);
            }
        },
        error: function (data, status) {
            console.log(data);
        }
    });
}