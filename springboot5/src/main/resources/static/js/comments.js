/**
 * comments.js
 * /post/details.html에 포함.
 * 댓글 생성, 목록, 수정, 삭제.
 */
document.addEventListener('DOMContentLoaded', () => {
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
        // 댓글이 달린 포스트의 아이디 찾기
        const postId = document.querySelector('input#id').value;

        // 댓글 내용
        const ctext = document.querySelector('textarea#commentText').value;

        // 댓글 작성자
        const writer = document.querySelector('input#commentWriter').value;

        if (ctext.trim() === '') {
            alert('댓글 내용을 입력하세요.')
            return;
        }

        // Ajax 요청에서 보낼 데이터
        const data = { postId, ctext, writer }; // 객체. 배열은 x = [] 이다.

        // Ajax Post 방식 요청을 보내고, 응답/에러 처리 콜백 등록.
        axios.post('/api/comment', data)
            .then((response) => {
                // TODO
                console.log(response);
            })
            .catch((error) => console.log(error));
    }


});
