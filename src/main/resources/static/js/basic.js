// 사용자가 내용을 올바르게 입력하였는지 확인합니다.
function isValidContents(contents) {
    if (contents == '') {
        alert('내용을 입력해주세요');
        return false;
    }
    if (contents.trim().length > 500) {
        alert('공백 포함 500자 이하로 입력해주세요');
        return false;
    }
    return true;
}

// 수정 버튼을 눌렀을 때, 기존 작성 내용을 textarea 에 전달합니다.
// 숨길 버튼을 숨기고, 나타낼 버튼을 나타냅니다.
function editPost(id) {
    showEdits(id);
    let title = $(`#${id}-title`).text().trim();
    let contents = $(`#${id}-content`).text().trim();
    let username = $(`#${id}-username`).text().trim();
    let date = $(`#${id}-date`).text().trim();
    $(`#${id}-title-textarea`).val(title);
    $(`#${id}-content-textarea`).val(contents);
    $(`#${id}-username-textarea`).val(username);
    $(`#${id}-date-textarea`).val(date);
}

function showEdits(id) {
    // title, content, username, date 텍스트 에어리어만 표시
    $(`#${id}-editarea-title`).show();
    $(`#${id}-editarea-content`).show();
    $(`#${id}-editarea-username`).show();
    $(`#${id}-editarea-date`).show();
    $(`#${id}-editarea-password`).show(); // 비밀번호 입력 필드 표시
    $(`#${id}-submit`).show();
    $(`#${id}-delete`).show();

    // 기존 텍스트는 숨김
    $(`#${id}-title`).hide();
    $(`#${id}-content`).hide();
    $(`#${id}-username`).hide();
    $(`#${id}-date`).hide();
    $(`#${id}-edit`).hide();

    // 이전 값 유지
    let prevTitle = $(`#${id}-title`).text().trim();
    let prevContents = $(`#${id}-content`).text().trim();
    let prevUsername = $(`#${id}-username`).text().trim();
    let prevDate = $(`#${id}-date`).text().trim();
    $(`#${id}-title-textarea`).val(prevTitle);
    $(`#${id}-content-textarea`).val(prevContents);
    $(`#${id}-username-textarea`).val(prevUsername);
    $(`#${id}-date-textarea`).val(prevDate);
}

$(document).ready(function() {
    // HTML 문서를 로드할 때마다 실행합니다.
    getMessages();

    // 수정 버튼 클릭 시 이벤트 설정
    $(document).on("click", ".icon-start-edit", function() {
        let id = $(this).attr("id").split("-")[0];
        showEdits(id);
    });
});

// 일정을 불러와서 보여줍니다.
function getMessages() {
    // 1. 기존 메모 내용을 지웁니다.
    $('#cards-box').empty();
    // 2. 일정 목록을 불러와서 HTML로 붙입니다.
    $.ajax({
        type: 'GET',
        url: '/api/schedulers',
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let message = response[i];
                let id = message['id'];
                let title = message['title'];
                let username = message['username'];
                let contents = message['contents'];
                let date = message['date'];
                addHTML(id, title, username, contents, date);
            }
        }
    });
}

// 메모 하나를 HTML로 만들어서 body 태그 내 원하는 곳에 붙입니다.
function addHTML(id, title, username, contents, date) {
    // 1. HTML 태그를 만듭니다.
    let tempHtml = `<div class="card">
<!-- date/username 영역 -->
<div class="metadata">
    <div id="${id}-date" class="date">
        ${date}
    </div>
    <div id="${id}-username" class="username">
        ${username}
    </div>
</div>
<!-- title 영역 -->
<div class="title">
    <div id="${id}-title" class="text">
        ${title}
    </div>
    <div id="${id}-editarea-title" class="edit">
        <textarea id="${id}-title-textarea" class="te-edit" name="" cols="30" rows="1"></textarea>
    </div>
</div>
<!-- contents 조회/수정 영역-->
<div class="contents">
    <div id="${id}-content" class="text">
        ${contents}
    </div>
    <div id="${id}-editarea-content" class="edit">
        <textarea id="${id}-content-textarea" class="te-edit" name="" cols="30" rows="5"></textarea>
    </div>
    <div id="${id}-editarea-username" class="edit">
        <textarea id="${id}-username-textarea" class="te-edit" name="" cols="30" rows="1"></textarea>
    </div>
    <div id="${id}-editarea-date" class="edit">
        <input type="date" id="${id}-date-textarea" class="field-date te-edit">
    </div>
    <div id="${id}-editarea-password" class="edit">
        <input type="password" id="${id}-password-textarea" class="te-edit" placeholder="비밀번호를 입력해주세요">
    </div>
</div>
<!-- 버튼 영역-->
<div class="footer">
    <img id="${id}-edit" class="icon-start-edit" src="images/edit.png" alt="수정" onclick="editPost('${id}')">
    <img id="${id}-delete" class="icon-delete" src="images/delete.png" alt="삭제" onclick="deleteOne('${id}')">
    <img id="${id}-submit" class="icon-end-edit" src="images/done.png" alt="완료" onclick="submitEdit('${id}')">
</div>
</div>`;
    // 2. #cards-box 에 HTML을 붙인다.
    $('#cards-box').append(tempHtml);
}

// 일정을 생성합니다.
function writePost() {
    // 1. 입력된 값을 읽어옵니다.
    let title = $('#title').val();
    let contents = $('#contents-textarea').val();
    let username = $('#username').val();
    let password = $('#password').val();
    let date = $('#date').val(); // 사용자가 선택한 날짜 값 읽기

    // 2. 유효성 검사를 진행합니다.
    if (!isValidContents(contents)) {
        return;
    }

    // 3. 일정을 생성합니다.
    let data = {title: title, contents: contents, username: username, password: password, date: date}; // date 포함
    $.ajax({
        type: 'POST',
        url: '/api/schedulers',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            // 4. 성공하면, 일정을 목록에 추가하고 입력 필드를 초기화합니다.
            addHTML(response.id, title, username, contents, date); // date 포함
            $('#title').val('');
            $('#contents-textarea').val('');
            $('#username').val('');
            $('#password').val('');
            $('#date').val(''); // 입력 필드 초기화
        }
    });
}

// 일정을 삭제합니다.
function deleteOne(id) {
    let password = prompt("비밀번호를 입력해주세요.");
    if (password == null) {
        return; // 사용자가 취소를 누른 경우
    }

    let data = {password};

    $.ajax({
        type: "DELETE",
        url: `/api/schedulers/${id}`,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(response) {
            alert('일정이 삭제되었습니다.');
            getMessages();
        },
        error: function(response) {
            if (response.status === 403) {
                alert('비밀번호가 올바르지 않습니다. 다시 시도해주세요.');
            }
        }
    });
}

// 수정을 반영합니다.
function submitEdit(id) {
    let title = $(`#${id}-title-textarea`).val().trim();
    let contents = $(`#${id}-content-textarea`).val().trim();
    let username = $(`#${id}-username-textarea`).val().trim();
    let date = $(`#${id}-date-textarea`).val().trim();
    let password = $(`#${id}-password-textarea`).val().trim();

    if (!isValidContents(contents)) {
        return;
    }

    let data = {title, contents, username, date, password};

    $.ajax({
        type: "PUT",
        url: `/api/schedulers/${id}`,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(response) {
            alert('일정이 수정되었습니다.');
            getMessages();
        },
        error: function(response) {
            if (response.status === 403) {
                alert('비밀번호가 올바르지 않습니다. 다시 시도해주세요.');
            }
        }
    });
}
