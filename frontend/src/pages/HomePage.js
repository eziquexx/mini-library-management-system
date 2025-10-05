import React from 'react';
import { useNavigate } from 'react-router-dom';

function HomePage() {

  const navigate = useNavigate();

  // LoginPage로 이동
  const goToLogin = () => {
    navigate('/login');
  }
  
  return (
    <div>
      <h1>도서 관리 시스템에 오신 것을 환영합니다!</h1>
      <button onClick={goToLogin}>로그인</button>
    </div>
  );
}

export default HomePage;