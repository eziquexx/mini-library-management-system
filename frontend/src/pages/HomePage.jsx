import React from "react";
import { useNavigate } from "react-router-dom";

const HomePage = () => {

  const navigate = useNavigate();

  const goToLoginPage = () => {
    navigate("/login");
  }

  return (
    <>
      <h1 className="text-3xl font-bold underline">메인 페이지</h1>
      <button onClick={goToLoginPage}>로그인 페이지 이동</button>
    </>
  );
}

export default HomePage;