const API_URL = 'http://' + window.location.host;

function isValidContents(contents) {
    if (contents === '') {
        alert('내용을 입력해주세요');
        return false;
    }
    if (contents.trim().length > 500) {
        alert('공백 포함 500자 이하로 입력해주세요');
        return false;
    }
    return true;
}

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
    $(`#${id}-editarea-title`).show();
    $(`#${id}-editarea-content`).show();
    $(`#${id}-editarea-username`).show();
    $(`#${id}-editarea-date`).show();
    $(`#${id}-editarea-password`).show();
    $(`#${id}-submit`).show();
    $(`#${id}-delete`).show();
    $(`#${id}-title`).hide();
    $(`#${id}-content`).hide();
    $(`#${id}-username`).hide();
    $(`#${id}-date`).hide();
    $(`#${id}-edit`).hide();
}

function isLoggedIn() {
    const token = localStorage.getItem('authToken');
    if (!token) {
        return false;
    }
    return $.ajax({
        type: 'POST',
        url: `${API_URL}/verify-token`,
        headers: {
            'Authorization': `Bearer ${token}`
        },
        contentType: 'application/json',
        async: false,
        success: function(response) {
            return true;
        },
        error: function(response) {
            return false;
        }
    }).responseJSON ? true : false;
}

$(document).ready(function() {
    getMessages();
    $(document).on("click", ".icon-start-edit", function() {
        let id = $(this).attr("id").split("-")[0];
        showEdits(id);
    });
});

function getMessages() {
    if (!isLoggedIn()) {
        alert('로그인이 필요합니다.');
        return;
    }
    $('#cards-box').empty();
    $.ajax({
        type: 'GET',
        url: `${API_URL}/schedulers`,
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('authToken')}`
        },
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

function addHTML(id, title, username, contents, date) {
    let tempHtml = `<div class="card">
        <div class="metadata">
            <div id="${id}-date" class="date">
                ${date}
            </div>
            <div id="${id}-username" class="username">
                ${username}
            </div>
        </div>
        <div class="title">
            <div id="${id}-title" class="text">
                ${title}
            </div>
            <div id="${id}-editarea-title" class="edit">
                <textarea id="${id}-title-textarea" class="te-edit" name="" cols="30" rows="1"></textarea>
            </div>
        </div>
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
        <div class="footer">
            <img id="${id}-edit" class="icon-start-edit" src="/images/edit.png" alt="수정" onclick="editPost('${id}')">
            <img id="${id}-delete" class="icon-delete" src="/images/delete.png" alt="삭제" onclick="deleteOne('${id}')">
            <img id="${id}-submit" class="icon-end-edit" src="/images/done.png" alt="완료" onclick="submitEdit('${id}')">
        </div>
    </div>`;
    $('#cards-box').append(tempHtml);
}

function writePost() {
    if (!isLoggedIn()) {
        alert('로그인이 필요합니다.');
        return;
    }
    let title = $('#title').val();
    let contents = $('#contents-textarea').val();
    let username = $('#username').val();
    let password = $('#password').val();
    let date = $('#date').val();
    if (!isValidContents(contents)) {
        return;
    }
    let data = {title: title, contents: contents, username: username, password: password, date: date};
    $.ajax({
        type: 'POST',
        url: `${API_URL}/schedulers`,
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('authToken')}`
        },
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            addHTML(response.id, title, username, contents, date);
            $('#title').val('');
            $('#contents-textarea').val('');
            $('#username').val('');
            $('#password').val('');
            $('#date').val('');
        }
    });
}

function deleteOne(id) {
    if (!isLoggedIn()) {
        alert('로그인이 필요합니다.');
        return;
    }
    let password = prompt("비밀번호를 입력해주세요.");
    if (password == null) {
        return;
    }
    let data = {password};
    $.ajax({
        type: "DELETE",
        url: `${API_URL}/schedulers/${id}`,
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('authToken')}`
        },
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

function submitEdit(id) {
    if (!isLoggedIn()) {
        alert('로그인이 필요합니다.');
        return;
    }
    let title = $(`#${id}-title-textarea`).val().trim();
    let contents = $(`#${id}-content-textarea`).val().trim();
    let username = $(`#${id}-username-textarea`).val().trim();
    let date = $(`#${id}-date-textarea`).val().trim();
    let password = $(`#${id}-password-textarea`).val().trim();
    if (!isValidContents(contents)) {
        return;
    }
    let data = {title: title, contents: contents, username: username, date: date, password: password};
    $.ajax({
        type: "PUT",
        url: `${API_URL}/schedulers/${id}`,
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('authToken')}`
        },
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

function login(username, password) {
    $.ajax({
        type: 'POST',
        url: `${API_URL}/login`,
        contentType: 'application/json',
        data: JSON.stringify({ username, password }),
        success: function(response) {
            localStorage.setItem('authToken', response.token);
            alert('로그인 성공!');
            getMessages();
        },
        error: function(response) {
            alert('로그인 실패. 다시 시도해주세요.');
        }
    });
}

function logout() {
    localStorage.removeItem('authToken');
    alert('로그아웃 되었습니다.');
}
