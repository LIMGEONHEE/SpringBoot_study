/**
 * comments.js
 * /post/details.html에 포함.
 * 댓글 생성, 목록, 수정, 삭제.
 */
document.addEventListener('DOMContentLoaded', () => {
    let currentPageNo = 0; // 현재 댓글 목록의 페이지 번호
    //-> getAllComments() 함수에서 Ajax 요청을 보내고, 정상 응답이 오면 현재 페이지 번호가 바뀜.
    //-> currentPageNo의 값은 [더보기] 버튼에서 사용.

    // bootstrap 라이브러리의 Collapse 객체를 생성:
    const bsCollapse = new bootstrap.Collapse('div#collapseComments', {toggle: false}); // {toggle: false}: 자바스크립트 객체를 넘김

    // [댓글 보기] 버튼을 찾아서 클릭 이벤트 리스너를 설정.
    const btnToggle = document.querySelector('button#btnToggle');
    btnToggle.addEventListener('click', () => {
        bsCollapse.toggle(); // Collapse 객체를 보기/감추기 토글.

        const toggle = btnToggle.getAttribute('data-toggle');
        if (toggle === 'collapse') { // === 는 왼쪽 변수의 타입과 오른쪽 변수의 타입까지 확인.
            btnToggle.innerHTML = '댓글 감추기';
            btnToggle.setAttribute('data-toggle', 'unfold'); // 감추기를 하면 댓글보기로 바뀌지 않아서 속성값을 새로 세팅. 'data-toggle'을 'unfold'로 바꿔준다.
        
            // 댓글 목록 불러오기:
            getAllComments(0);
        } else{
            btnToggle.innerHTML = '댓글 보기';
            btnToggle.setAttribute('data-toggle', 'collapse');
        }
 
    });

    // 댓글 [등록] 버튼을 찾아서, 클릭 이벤트 리스너를 설정:
    const btnRegisterComment = document.querySelector('button#btnRegisterComment');
    btnRegisterComment.addEventListener('click', registerComment);
    

     // 댓글 [더보기] 버튼을 찾아서, 클릭 이벤트 리스너를 설정:
    const btnMoreComments = document.querySelector('button#btnMoreComments');
    btnMoreComments.addEventListener('click', () => getAllComments(currentPageNo + 1));


    //----- 함수 정의(선언) ------------------------------------------------------------------------------
    function registerComment() {
        // 댓글이 달린 포스트의 아이디
        const postId = document.querySelector('input#id').value;
        // 댓글 내용
        const ctext = document.querySelector('textarea#commentText').value;
        // 댓글 작성자
        const writer = document.querySelector('input#commentWriter').value;
        
        if (ctext.trim() === '') {
            alert('댓글 내용을 입력하세요.');
            return;
        }

        // Ajax 요청에서 보낼 데이터
        const data = { postId, ctext, writer }; // 객체. 배열은 x = [] 이다.

        // Ajax POST 방식 요청을 보냄고, 응답/에러 처리 콜백 등록.
        axios.post('/api/comment', data)
            .then((response) => {
                console.log(response,data);
                alert('댓글 등록 성공!');

                // 댓글 내용 입력란 비우기.
                document.querySelector('textarea#commentText').value = '';

                // 댓글 목록 갱신
                getAllComments(0);
            })
            .catch((error) => console.log(error));
    }

    function getAllComments(pageNo) {
        // 댓글들이 달린 포스트 아이디:
        
        const postId = document.querySelector('input#id').value;
        
        // Ajax 요청을 보낼 주소:
        // path variable: 댓글이 달린 포스트 아이디. request param: 페이지 번호.
        const uri = `/api/comment/all/${postId}?p=${pageNo}`;
        
        // Ajax 요청을 보내고, 성공/실패 콜백 설정:
        axios.get(uri)
            .then((response) => {
                console.log(response);
                currentPageNo = response.data.number;

                // 현재 페이지 번호보다 페이지 개수가 더 많으면 댓글 [더보기]
                const divBtnMore = document.querySelector('div#divBtnMore');
                if (currentPageNo + 1 < response.data.totalPages) { // (!response.data.last)로 해도 된다.
                    divBtnMore.classList.remove('d-none');
                } else {
                    divBtnMore.classList.add('d-none');
                }

                // 댓글 목록을 HTML로 작성
                makeCommentElements(response.data.content, response.data.number); // content는 배열, number은 현재 페이지 번호
            })
            .catch((error) => console.log(error));
    }

    function makeCommentElements(data, pageNo) {
        // 댓글 목록을 추가할 div 요소
        const divComments = document.querySelector('div#divComments')

        let htmlStr = ''; // div에 삽입할 html 코드(댓글 목록)
        for (let comment of data) { //배열에서 in을 쓰면 인덱스를 주고, of를 주면 각 요소의 값을 반복한다.
            // console.log(comment);
            htmlStr += `
            <div class="card card-body mt-2">
                <div class="mt-2">
                    <span class="fw-bold">${comment.writer}</span>
                    <span class="text-secondary">${comment.modifiedTime}</span>
                </div>
                <div class="mt-2">
                    <div class="mt-2">
                        <textarea class="commentText form-control" data-id="${comment.id}">${comment.ctext}</textarea>
                    </div>
                    <div class="mt-2">
                        <button class="btnDelete btn btn-outline-danger btn-sm" data-id="${comment.id}">삭제</button>
                        <button class="btnUpdate btn btn-outline-primary btn-sm" data-id="${comment.id}">수정</button>
                    </div>
                </div>

            </div>
            `;
        }

        if (pageNo === 0) {
            // 댓글 목록 첫번째 페이지이면, 기존 내용을 다 지우고 새로 작성.
            divComments.innerHTML = htmlStr;
        } else {
            // 댓글 목록에서 첫번째 페이지가 아니면, 기존 내용 밑에 추가(append)
            divComments.innerHTML += htmlStr;
        } 

        // 댓글 [삭제], [수정] 버튼들의 이벤트 리스너는 버튼들이 생겨난 이후에 등록!
        // 모든 button.btnDelete 버튼들을 찾아서 클릭 이벤트 리스너를 등록.
        const btnDeletes = document.querySelectorAll('button.btnDelete'); // 여러개를 찾을 것이기 때문에 SelectorAll을 선택.
        btnDeletes.forEach((btn) => {
            btn.addEventListener('click', deleteComment); // deleteComment는 함수를 만들고 컨트롤러에서도 작성해야한다.
        });

        const btnUpdates = document.querySelectorAll('button.btnUpdate');
        btnUpdates.forEach((btn) => {
            btn.addEventListener('click', updateComment);
        });

    }

    function deleteComment(event) { // event 타켓을 찾기 위해 아규먼트에 넣어줘야한다.
        // console.log(event);
        // console.log(event.target);
        if (!confirm('정말 삭제할까요?')) {
            return;
        }

        const id = event.target.getAttribute('data-id'); // 삭제할 댓글 아이디
        const uri = `/api/comment/${id}`; // 삭제 Ajax 요청을 보낼 주소
        axios.delete(uri)
            .then((response) => {
                console.log(response);
                alert(`댓글 #${id} 삭제 성공`)
                getAllComments(0); // 댓글 목록 갱신
            }) 
            .catch((error) => console.log(error)); // (() => ())한문장 밖에 없으면 {}가 아니라 ()를 쓸 수 있고 세미클론(;)도 생략가능하다.
    }

    function updateComment(event) {
        // console.log(event.target);
        const id = event.target.getAttribute('data-id'); // 업데이트할 댓글 아이디
        
        const textarea = document.querySelector(`textarea.commentText[data-id="${id}"]`); // ${id}는 이벤트가 타겟에서 찾은 데이터id 속성값. 속성값을 쓸 때에는 대괄호[] 사용.
        // console.log(textarea);

        const ctext = textarea.value; // 업데이트할 댓글 내용
        if (ctext.trim() === ''){
            alert('댓글 내용은 반드시 입력해야 합니다.');
            return;
        }

        if (!confirm('변경된 댓글을 저장할까요?')) {
            return;
        } 

        const uri = `/api/comment/${id}`; // Ajax 요청을 보낼 주소
        const data = { id, ctext}; // 업데이트 요청 데이터. {id: id, ctext: ctext} 프로퍼티 이름이 지역변수와 이름이 같으면 생략가능.
        axios.put(uri, data)
            .then((response) => {
                console.log(response);
                alert(`댓글 #${id} 업데이트 성공!`);
                getAllComments(0); // 댓글 목록 갱신
            })
            .catch((error) => console.log(error));
    }


});
