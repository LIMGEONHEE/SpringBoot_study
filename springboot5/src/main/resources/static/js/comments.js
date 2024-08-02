/**
 * comments.js
 * /post/details.html에 포함.
 * 댓글 생성, 목록, 수정, 삭제.
 */
document.addEventListener('DOMContentLoaded', () => {
    let currentpageNo = 0; // 현재 페이지 번호.
    let totalPages = 0; //댓글 목록 전체 페이지 개수.

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
            getAllComment(0);
        } else{
            btnToggle.innerHTML = '댓글 보기';
            btnToggle.setAttribute('data-toggle', 'collapse');
        }

    });

    // 댓글 [등록] 버튼을 찾아서, 클릭 이벤트 리스너를 설정:
    const btnRegisterComment = document.querySelector('button#btnRegisterComment');
    btnRegisterComment.addEventListener('click', registerComment);
    

    // 댓글 [더보기] 버튼을 찾아서, 클릭 이벤트 리스너를 설정:
    const btnMoreCommnets = document.querySelector('button#btnMoreCommnets');
    btnMoreCommnets.addEventListener('click', () => {});  // TODO


    //----- 함수 정의(선언) -----
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

                // TODO: 댓글 목록 갱신
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
                // TODO: 댓글 목록을 HTML로 작성
            })
            .catch((error) => console.log(error));
    }
});
