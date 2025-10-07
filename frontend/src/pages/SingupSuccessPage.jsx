import React from "react";
import { useNavigate } from "react-router-dom";

const SignupSuccessPage = () => {

  const navigate = useNavigate();

  const goToLogin = () => {
    navigate('/login');
  }

  return (
    <>
      <div className="w-full flex justify-center">
        <div className="px-7 md:px-10 lg:px-14 py-30 text-center">
          <h1 className="text-3xl font-bold mb-4">회원가입 성공</h1>
          <div className="mb-6">회원가입에 성공하였습니다.</div>
          <div className="mb-60">
            <button 
              onClick={ goToLogin }
              className="
                px-6
                py-3
                text-lg
                font-semibold
                hover:text-teal-700
                cursor-pointer
              "
            >로그인하러 가기 &rarr;</button>
          </div>

        </div>
      </div>
    </>
  );
}

export default SignupSuccessPage;