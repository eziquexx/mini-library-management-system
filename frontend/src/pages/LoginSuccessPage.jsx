import React from "react";
import { useNavigate } from "react-router-dom";


function LoginSuccessPage () {
  const navigate = useNavigate();

  const goToHome = () => {
    navigate('/');
  }

  return (
    <div>
      <h1>로그인 성공!</h1>
      <button onClick={goToHome}>홈으로</button>
    </div>
  );
}

export default LoginSuccessPage;