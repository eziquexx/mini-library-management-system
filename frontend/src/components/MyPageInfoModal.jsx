import axios from "axios";
import React, { useState } from "react"; 
import useUserStore from "../stores/useUserStore";
import { useNavigate } from "react-router-dom";


const modalData = {
  dialogEmail: {
    title: "이메일 수정",
    content: "새로운 이메일 주소를 입력해주세요.",
    placeholder: "새 이메일 입력",
  },
  dialogPw: {
    title: "비밀번호 수정",
    content: "새로운 비밀번호를 입력해주세요.",
    placeholderOri: "새 비밀번호 입력",
    placeholderRe: "새 비밀번호 확인 입력",
  },
  dialogDelAccount: {
    title: "회원 탈퇴",
    content: "사용중인 비밀번호를 입력해주세요.",
    placeholder: "비밀번호 입력",
  }
}

const MyPageInfoModal = ({id}) => {

  const { title, content, placeholder, placeholderOri, placeholderRe } = modalData[id] || {};
  const fetchUser = useUserStore((state) => state.fetchUser);
  const [emailValue, setEmailValue] = useState();
  const [pwValue, setPwValue] = useState();
  const [repwValue, setRepwValue] = useState();
  const [delPwValue, setDelPwValue] = useState();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [data, setData] = useState([]);
  const navigate = useNavigate();

  const apiUrl = import.meta.env.VITE_API_URL;

  // 이메일 수정 api
  const updateEmail = async () => {
    try {
      const response = await axios.patch(
        `${apiUrl}/api/v1/user/me/email`,
        { email: emailValue },
        { withCredentials: true },
      );

      console.log("이메일 변경: ",response.data.data)
      setData(response.data.data);
      alert("이메일 변경 성공");

    } catch (error) {
      console.log("Error: ", error);
      setError(error.response.data);
      alert(error.response.data.message);
    } finally {
      setLoading(false);
    }
  }

  // 비밀번호 수정 api
  const updatePw = async () => {
    try {
      const response = await axios.patch(
        `${apiUrl}/api/v1/user/me/password`,
        { password: pwValue,
          repassword: repwValue,
        },
        { withCredentials: true },
      );

      console.log("비밀번호 변경: ",response.data.data);
      setData(response.data.data);
      alert("비밀번호 변경 성공");

    } catch (error) {
      console.log("Error: ", error);
      setError(error.response.data);
      alert(error.response.data.message);
    } finally {
      setLoading(false);
    }
  }

  // 회원 탈퇴 api
  const delAccount = async () => {
    try {
      const response = await axios.post(
        `${apiUrl}/api/v1/user/me/withdraw`,
        { password: delPwValue },
        { withCredentials: true },
      );

      console.log("회원탈퇴: ", response.data.data);
      setData(response.data.data);
      alert("회원탈퇴 성공");
      navigate("/");
      
    } catch (error) {
      console.log("Error: ", error.response.data);
      setError(error.response.data);
      alert(error.response.data.message);
    } finally {
      setLoading(false);
    }
  }

  // 버튼 클릭 시 id에 따라 api 호출
  const handleClick = async () => {
    if (id === "dialogEmail") {
      await updateEmail();
    } else if (id === "dialogPw") {
      await updatePw();
    } else if (id === "dialogDelAccount") {
      await delAccount();
    }

    await fetchUser(); 
    setEmailValue("");
    setPwValue("");
    setRepwValue("");
    setDelPwValue("");
    document.getElementById(id)?.close();
  }

  return (
    <>
      {/* <button command="show-modal" commandfor="dialog" class="rounded-md bg-gray-950/5 px-2.5 py-1.5 text-sm font-semibold text-gray-900 hover:bg-gray-950/10">Open dialog</button> */}
      <el-dialog>
        <dialog id={id} aria-labelledby="dialog-title" class="fixed inset-0 size-auto max-h-none max-w-none overflow-y-auto bg-transparent backdrop:bg-transparent">
          {/* 배경 */}
          <el-dialog-backdrop class="fixed inset-0 bg-gray-500/75 transition-opacity data-closed:opacity-0 data-enter:duration-300 data-enter:ease-out data-leave:duration-200 data-leave:ease-in"></el-dialog-backdrop>

          <div tabindex="0" class="flex min-h-full items-end justify-center p-4 text-center focus:outline-none sm:items-center sm:p-0">
            <el-dialog-panel class="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all data-closed:translate-y-4 data-closed:opacity-0 data-enter:duration-300 data-enter:ease-out data-leave:duration-200 data-leave:ease-in sm:my-8 sm:w-full sm:max-w-lg data-closed:sm:translate-y-0 data-closed:sm:scale-95">
              <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                <div class="sm:flex sm:items-start">
                  {/* <div class="mx-auto flex size-12 shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-0 sm:size-10">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" data-slot="icon" aria-hidden="true" class="size-6 text-red-600">
                      <path d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126ZM12 15.75h.007v.008H12v-.008Z" stroke-linecap="round" stroke-linejoin="round" />
                    </svg>
                  </div> */}

                  {/* 이메일 변경 */}
                  { id === "dialogEmail" && (
                    <>
                      <div class="flex flex-col w-full mt-3 text-center sm:mt-0 sm:text-left">
                        <h3 id="dialog-title" class="text-base font-semibold text-gray-900">{title}</h3>
                        <div class="mt-2">
                          <p class="text-sm text-gray-500 mb-3">{content}</p>
                          <input 
                            type="text" 
                            placeholder={placeholder}
                            className="
                              border p-2 border-gray-300 outline-none
                              w-full
                              text-sm 
                              focus:border-teal-600
                            "
                            value={emailValue}
                            onChange={ (e) => setEmailValue(e.target.value) }
                          />
                        </div>
                      </div>
                    </>
                  )}

                  {/* 비밀번호 변경 */}
                  { id === "dialogPw" && (
                    <>
                      <div class="flex flex-col w-full mt-3 text-center sm:mt-0 sm:text-left">
                        <h3 id="dialog-title" class="text-base font-semibold text-gray-900">{title}</h3>
                        <div class="mt-2">
                          <p class="text-sm text-gray-500 mb-3">{content}</p>
                          <input 
                            type="text" 
                            placeholder={placeholderOri}
                            className="
                              border p-2 border-gray-300 outline-none
                              w-full
                              text-sm 
                              focus:border-teal-600
                            "
                            value={pwValue}
                            onChange={ (e) => setPwValue(e.target.value)}
                          />
                          <input 
                            type="text" 
                            placeholder={placeholderRe}
                            className="
                              border p-2 border-gray-300 outline-none
                              w-full
                              text-sm 
                              focus:border-teal-600
                            "
                            value={repwValue}
                            onChange={ (e) => setRepwValue(e.target.value) }
                          />
                        </div>
                      </div>
                    </>
                  )}

                  {/* 회원탈퇴 */}
                  { id === "dialogDelAccount" && (
                    <>
                      <div class="flex flex-col w-full mt-3 text-center sm:mt-0 sm:text-left">
                        <h3 id="dialog-title" class="text-base font-semibold text-gray-900">{title}</h3>
                        <div class="mt-2">
                          <p class="text-sm text-gray-500 mb-3">{content}</p>
                          <input 
                            type="text" 
                            placeholder={placeholder}
                            className="
                              border p-2 border-gray-300 outline-none
                              w-full
                              text-sm 
                              focus:border-teal-600
                            "
                            value={delPwValue}
                            onChange={ (e) => setDelPwValue(e.target.value) }
                          />
                        </div>
                      </div>
                    </>
                  )}
                  
                </div>
              </div>
              <div class="px-4 pb-7 sm:pb-5 sm:flex sm:flex-row-reverse sm:px-6">
                <button 
                  type="button" command="close" commandfor={id} 
                  class="
                    inline-flex w-full justify-center rounded-md 
                    bg-teal-600 hover:bg-teal-700 
                    text-sm font-semibold text-white shadow-xs 
                    px-3 py-2 sm:ml-3 sm:w-auto
                  "
                  onClick={handleClick}
                >{id === 'dialogDelAccount' ? "탈퇴" : "변경"}</button>
                <button type="button" command="close" commandfor={id} class="mt-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-xs inset-ring inset-ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto">취소</button>
              </div>
            </el-dialog-panel>
          </div>
        </dialog>
      </el-dialog>
    </>
  );
}

export default MyPageInfoModal;