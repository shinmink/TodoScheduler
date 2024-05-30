// 백엔드 API 엔드포인트 설정
const API_URL = 'http://' + window.location.host;

$(document).ready(function () {
    const auth = getToken();

    if (auth !== undefined && auth !== '') {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', auth);
        });
    } else {
        window.location.href = host + '/api/user/login-page';
        return;
    }

    $.ajax({
        type: 'GET',
        url: `/api/user-info`,
        contentType: 'application/json',
    })
        .done(function (res, status, xhr) {
            const username = res.username;
            const isAdmin = !!res.admin;

            if (!username) {
                window.location.href = '/api/user/login-page';
                return;
            }

            $('#username').text(username);
            if (isAdmin) {
                $('#admin').text(true);
                showProduct();
            } else {
                showProduct();
            }

            // 로그인한 유저의
            $.ajax({
                type: 'POST',
                url: `/api/schedulers`,
                error(error) {
                    logout();
                }
            }).done(function (fragment) {
                $('#fragment').replaceWith(fragment);
            });

        })
        .fail(function (jqXHR, textStatus) {
            logout();
        });

    // id 가 query 인 녀석 위에서 엔터를 누르면 execSearch() 함수를 실행하라는 뜻입니다.
    $('#query').on('keypress', function (e) {
        if (e.key == 'Enter') {
            execSearch();
        }
    });
    $('#close').on('click', function () {
        $('#container').removeClass('active');
    })
    $('#close2').on('click', function () {
        $('#container2').removeClass('active');
    })
    $('.nav div.nav-see').on('click', function () {
        $('div.nav-see').addClass('active');
        $('div.nav-search').removeClass('active');

        $('#see-area').show();
        $('#search-area').hide();
    })
    $('.nav div.nav-search').on('click', function () {
        $('div.nav-see').removeClass('active');
        $('div.nav-search').addClass('active');

        $('#see-area').hide();
        $('#search-area').show();
    })

    $('#see-area').show();
    $('#search-area').hide();
})

// 사용자가 내용을 올바르게 입력하였는지 확인합니다.
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
}

// 로그인 상태 확인
function isLoggedIn() {
    // 로컬 스토리지에서 토큰을 가져옵니다.
    const token = localStorage.getItem('authToken');

    // 토큰이 없으면 로그인 상태가 아닙니다.
    if (!token) {
        return false;
    }

    // 토큰이 있으면 서버에 유효성을 확인합니다.
    return $.ajax({
        type: 'POST',
        url: `${API_URL}/verify-token`,
        headers: {
            'Authorization': `Bearer ${token}`
        },
        contentType: 'application/json',
        async: false, // 동기 요청
        success: function(response) {
            return true;
        },
        error: function(response) {
            return false;
        }
    }).responseJSON ? true : false; // 동기 요청 결과를 반환
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
            <img id="${id}-edit" class="icon-start-edit" src="/images/edit.png" alt="수정" onclick="editPost('${id}')">
            <img id="${id}-delete" class="icon-delete" src="/images/delete.png" alt="삭제" onclick="deleteOne('${id}')">
            <img id="${id}-submit" class="icon-end-edit" src="/images/done.png" alt="완료" onclick="submitEdit('${id}')">
        </div>
    </div>`;
    // 2. #cards-box 에 HTML을 붙인다.
    $('#cards-box').append(tempHtml);
}

// 일정을 생성합니다.
function writePost() {
    if (!isLoggedIn()) {
        alert('로그인이 필요합니다.');
        return;
    }

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
        url: `${API_URL}/schedulers`,
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('authToken')}`
        },
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
    if (!isLoggedIn()) {
        alert('로그인이 필요합니다.');
        return;
    }

    let password = prompt("비밀번호를 입력해주세요.");
    if (password == null) {
        return; // 사용자가 취소를 누른 경우
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

// 수정을 반영합니다.
function submitEdit(id) {
    if (!isLoggedIn()) {
        alert('로그인이 필요합니다.');
        return;
    }

    let title = $(`#${id}-title-textarea`).val().trim();
    let contents = $(`#${id}-content-textarea`).val().trim();
    let username = $(`#${id}-username-textarea`).val().trim();
    let date = $(`#${id}-date-textarea`).val().trim();
    let password = $(`#${id}-password-textarea`).val().trim(); // 비밀번호 필드 추가

    if (!isValidContents(contents)) {
        return;
    }

    let data = {title: title, contents: contents, username: username, date: date, password: password}; // date와 password 포함

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

// 로그인 함수
function login(username, password) {
    $.ajax({
        type: 'POST',
        url: `${API_URL}/login`,
        contentType: 'application/json',
        data: JSON.stringify({ username, password }),
        success: function(response) {
            // 서버로부터 받은 토큰을 로컬 스토리지에 저장
            localStorage.setItem('authToken', response.token);
            alert('로그인 성공!');
            getMessages(); // 로그인 후 일정을 다시 불러옵니다.
        },
        error: function(response) {
            alert('로그인 실패. 다시 시도해주세요.');
        }
    });
}

// 로그아웃 함수
function logout() {
    // 로컬 스토리지에서 토큰 삭제
    localStorage.removeItem('authToken');
    alert('로그아웃 되었습니다.');
}
