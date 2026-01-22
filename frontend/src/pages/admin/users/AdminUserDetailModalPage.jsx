import { DialogTitle } from "@headlessui/react";
import axios from "axios";
import { useEffect, useState } from "react";

// 권한 role
const roleOptions = ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER'];
// 상태 status (활성화, 비활성화(사용자가 탈퇴요청), 정지, 삭제된 상태)
const statusOptions = ['ACTIVE', 'INACTIVE', 'SUSPENDED', 'DELETED'];

const AdminUserDetailModalPage = ({onClose, item, apiUrl, onDataUpdated}) => {

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [data, setData] = useState([]);
  const id = item;

  // 권한 role
  const [selectedRoleOpt, setSelectedRoleOpt] = useState('');
  const [availableRoleOpt, setAvailableRoleOpt] = useState([]);

  // 상태 status
  const [selectedStatusOpt, setSelectedStatusOpt] = useState('');
  const [availableStatusOpt, setAvailableStatusOpt] = useState([]);

  // 권한, 상태 변경 여부
  const [roleChange, setRoleChange] = useState(false);
  const [statusChange, setStatusChange] = useState(false);

  // 권한, 상태 변경
  const [childValChange, setChildValChange] = useState(false);

  // 탈퇴처리 유무
  const [deletedChange, setDeletedChange] = useState(false);

  // 이미 탈퇴처리
  const [alreadyDeleted, setAlreadyDeleted] = useState(true);


  // 상세 페이지 조회
  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      setError(null);

      try {
        const response = await axios.get(
          `${apiUrl}/api/v1/admin/users/${id}`,
          {
            withCredentials: true,
            headers: {
              Accept: "application/json"
            }
          }
        );

        console.log(response.data.data);
        setData(response.data.data);

        // 권한 role
        const remainingRoleOpts = roleOptions.filter(option => option !== response.data.data.role);
        setAvailableRoleOpt(remainingRoleOpts);
        setSelectedRoleOpt(response.data.data.role);

        // 상태 status
        const remainingStatusOpts = statusOptions.filter(option => option != response.data.data.status);
        setAvailableStatusOpt(remainingStatusOpts);
        setSelectedStatusOpt(response.data.data.status);

        

      } catch (error) {
        setError(error);
        console.log(error);
      } finally {
        setLoading(false);
      }
    }

    fetchData();
  }, [apiUrl, id]);


  // 권한 수정
  const handleRoleChange = (e) => {
    setSelectedRoleOpt(e.target.value);
  };

  // 상태 수정
  const handleStatusChange = (e) => {
    setSelectedStatusOpt(e.target.value);
  }
  
  useEffect(() => {
    // 권한 변경 감지
    if (data.role !== selectedRoleOpt) {
      setRoleChange(true); // 변경가능
    } else {
      setRoleChange(false); // 변경불가
    }

    // 상태 변경 감지
    if (data.status !== selectedStatusOpt) {
      setStatusChange(true);
    } else {
      setStatusChange(false);
    }

    if (data.role !== selectedRoleOpt || data.status !== selectedStatusOpt) {
      setChildValChange(true);
    } else {
      setChildValChange(false);
    }

    // 탈퇴처리 버튼 활성화
    if (data.status === 'DELETED' ) { // 활성화 조건
      if (data.username.includes('_deleted_')) {
        setDeletedChange(false);
        setAlreadyDeleted(false);
      } else {
        setDeletedChange(true);
        setAlreadyDeleted(true);
      }
    }

    console.log(data.role);
    
  }, [selectedRoleOpt, selectedStatusOpt, data.role, data.status, roleChange, statusChange]);


  // Role, Status 값 수정
  const onUpdateValue = async () => {
    try {
      if (data.role !== selectedRoleOpt) { await fetchRoleUpdate(); }
      if (data.status !== selectedStatusOpt) { await fetchStatusUpdate(); }

      onClose();
      onDataUpdated();
    } catch (err) {
      console.error('수정 실패: ', err);
    }
  }

  // 탈퇴 처리
  const onUpdateDeleted = () => {
    fetchDeletedUpdate();

    onClose();
    onDataUpdated();

  }

  // role 변경 api
  const fetchRoleUpdate = async () => {
    try {
      await axios.patch(
        `${apiUrl}/users/${id}/role`,
        {
          "role": selectedRoleOpt
        }, 
        {
          withCredentials: true,
          headers: {
            Accept: "application/json"
          }
        });

      alert("수정 성공");
    } catch(err) {
      console.log("error: ", err);
      setError(error);
    } 
  }

  // status 변경 api
  const fetchStatusUpdate = async () => {
    try {
      await axios.patch(
        `${apiUrl}/users/${id}/status`, 
        {
          "status": selectedStatusOpt
        },
        {
          withCredentials: true,
          headers: {
            Accept: "application/json"
          }
        })

      alert("수정 성공");
    } catch(error) {
      console.log("error: ", error);
      setError(error);
    }
  }

  // DELETED 탈퇴처리 api
  const fetchDeletedUpdate = async () => {
    try {
      await axios.patch(
        `${apiUrl}/users/${id}/deactivate`, 
        null,
        {
          withCredentials: true,
        }
      )
      alert("탈퇴처리 성공");
    } catch (err) {
      console.error("에러 발생: ", err);
      setError(err);
    }
  }


  return (
    <>
      <div className="bg-white px-3 pt-5 pb-4 ">
        <div className="sm:flex sm:items-start">
          {/* <div className="mx-auto flex size-12 shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-0 sm:size-10">
            <ExclamationTriangleIcon aria-hidden="true" className="size-6 text-red-600" />
          </div> */}
          <div className="mx-3 text-left">
            <div className="flex flex-row justify-end text-gray-700">
              <button onClick={onClose} className="cursor-pointer">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="size-6">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M6 18 18 6M6 6l12 12" />
                </svg>
              </button>
            </div>
            <DialogTitle as="h3" className="text-base text-center font-semibold text-gray-900 sm:text-left mt-1">
              사용자 상세페이지
            </DialogTitle>
            <div className="mt-2">
              <div className="text-sm text-gray-500">
                <div className="w-full mt-4">
                  { loading && <div>불러오는 중...</div> }
                  { error && <div style={{ color: "red" }}>{error}</div> }

                  { !loading && !error && (
                    <>
                      <table className="w-full table-fixed border-collapse text-sm ">
                        <tbody className="">
                          <tr className="w-full">
                            <td className="px-3 py-3 w-2/6 border border-gray-300">id</td>
                            <td className="px-3 py-3 w-4/6 border border-gray-300">{data.id ? data.id : "-"}</td>
                          </tr>
                          <tr className="w-full">
                            <td className="px-3 py-3 w-2/6 border border-gray-300">username </td>
                            <td className="px-3 py-3 w-4/6 border border-gray-300">
                              {data.username ? data.username : "-"}
                              {alreadyDeleted ? "" : <div className="text-red-600"> ※ 탈퇴처리가 된 회원입니다.</div>}
                            </td>
                          </tr>
                          <tr className="w-full">
                            <td className="px-3 py-3 w-2/6 border border-gray-300">email</td>
                            <td className="px-3 py-3 w-4/6 border border-gray-300">{data.email ? data.email : "-"}</td>
                          </tr>
                          <tr className="w-full">
                            <td className="px-3 py-3 w-2/6 border border-gray-300">role</td>
                            <td className="px-3 py-3 w-4/6 border border-gray-300">
                              {alreadyDeleted ? 
                                <select 
                                  name="roles" 
                                  id="roles" 
                                  value={selectedRoleOpt}
                                  onChange={handleRoleChange}
                                  disabled={!alreadyDeleted}
                                  className="border-1 outline-none px-0.5 pb-0.5 text-sm"
                                >
                                  <option value={data.role || ""}>{data.role ? data.role : "-"}</option>
                                  {availableRoleOpt.map((option, index) => (
                                    <option key={index} value={option}>
                                      {option}
                                    </option>
                                  ))}
                                </select> : data.role
                              }
                            </td>
                          </tr>
                          <tr className="w-full">
                            <td className="px-3 py-3 w-2/6 border border-gray-300">status</td>
                            <td className="px-3 py-3 w-4/6 border border-gray-300">
                              {alreadyDeleted ? 
                                <select 
                                  name="roles" 
                                  id="roles" 
                                  value={selectedStatusOpt}
                                  onChange={handleStatusChange}
                                  disabled={!alreadyDeleted}
                                  className="border-1 outline-none px-0.5 pb-0.5 text-sm"
                                >
                                  <option value={data.status || ""}>{data.status ? data.status : "-"}</option>
                                  {availableStatusOpt.map((option, index) => (
                                    <option key={index} value={option}>
                                      {option}
                                    </option>
                                  ))}
                                </select> : 
                                <span className="text-red-600">{data.status}</span>
                              }
                            </td>
                          </tr>
                          <tr className="w-full">
                            <td className="px-3 py-3 w-2/6 border border-gray-300">join_date</td>
                            <td className="px-3 py-3 w-4/6 border border-gray-300">{data.joinDate ? data.joinDate.split('T').join(' / ') : "-"}</td>
                          </tr>
                          <tr className="w-full">
                            <td className="px-3 py-3 w-2/6 border border-gray-300">last_login_date</td>
                            <td className="px-3 py-3 w-4/6 border border-gray-300">{data.lastLoginDate ? data.lastLoginDate.split('T').join(' / ') : "-"}</td>
                          </tr>
                          <tr className="w-full">
                            <td className="px-3 py-3 w-2/6 border border-gray-300">updated_at</td>
                            <td className="px-3 py-3 w-4/6 border border-gray-300">{data.updatedAt ? data.updatedAt.split('T').join(' / ') : "-"}</td>
                          </tr>
                          <tr className="w-full">
                            <td className="px-3 py-3 w-2/6 border border-gray-300">inactive_at</td>
                            <td className="px-3 py-3 w-4/6 border border-gray-300">{data.inactiveAt ? data.inactiveAt.split('T').join(' / ') : "-"}</td>
                          </tr>
                          <tr className="w-full">
                            <td className="px-3 py-3 w-2/6 border border-gray-300">deleted_at</td>
                            <td className="px-3 py-3 w-4/6 border border-gray-300">{data.deletedAt ? data.deletedAt.split('T').join(' / ') : "-"}</td>
                          </tr>
                        </tbody>
                      </table>
                    </>
                  )}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* buttons */}
      <div className="bg-gray-50 px-7 py-4 flex flex-row justify-between">
        <div>
          {alreadyDeleted ? 
            <button
              type="button"
              onClick={onUpdateDeleted}
              disabled={!deletedChange}
              className={`inline-flex justify-center rounded-md ${deletedChange ? "bg-red-600 hover:bg-red-500" : ""} bg-gray-400 px-3 py-2 text-sm font-semibold text-white shadow-xs sm:w-auto mr-3 cursor-pointer`}
            >
              탈퇴처리
            </button> : 
            ""
          }
        </div>
        
        <div>
          {alreadyDeleted ? 
            <button
              type="button"
              onClick={onUpdateValue}
              disabled={!childValChange}
              className={`inline-flex justify-center rounded-md ${childValChange ? "bg-red-600 hover:bg-red-500" : ""} bg-gray-400 px-3 py-2 text-sm font-semibold text-white shadow-xs sm:w-auto mr-3 cursor-pointer`}
            >
              수정
            </button> : ""
          }
          <button
            type="button"
            data-autofocus
            onClick={onClose}
            className="inline-flex justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-xs inset-ring inset-ring-gray-300 hover:bg-gray-50 sm:w-auto cursor-pointer"
          >
            닫기
          </button>
        </div>
      </div>
    </>
  );
}

export default AdminUserDetailModalPage;