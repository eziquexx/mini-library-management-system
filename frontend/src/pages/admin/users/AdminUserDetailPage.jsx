import axios from "axios";
import { useEffect, useState } from "react";

// 권한 role
const roleOptions = ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER'];
// 상태 status (활성화, 비활성화(사용자가 탈퇴요청), 정지, 삭제된 상태)
const statusOptions = ['ACTIVE', 'INACTIVE', 'SUSPENDED', 'DELETED'];

const AdminUserDetailPage = ({item, apiUrl, onValueChange, onChangedValue, onOriginValue}) => {

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

  
  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      setError(null);

      try {
        const response = await axios.get(
          `${apiUrl}/users/${id}`,
          {
            withCredentials: true,
            headers: {
              Accept: "application/json"
            }
          }
        );

        // console.log(response.data.data);
        setData(response.data.data);

        // 권한 role
        const remainingRoleOpts = roleOptions.filter(option => option !== response.data.data.role);
        setAvailableRoleOpt(remainingRoleOpts);
        setSelectedRoleOpt(response.data.data.role);

        // 상태 status
        const remainingStatusOpts = statusOptions.filter(option => option != response.data.data.status);
        setAvailableStatusOpt(remainingStatusOpts);
        setSelectedStatusOpt(response.data.data.status);

        onOriginValue([response.data.data.role, response.data.data.status]);
        
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
      onValueChange(true);
      onChangedValue([selectedRoleOpt, selectedStatusOpt]);
    } else {
      onValueChange(false);
    }
  }, [selectedRoleOpt, selectedStatusOpt, data.role, data.status, roleChange, statusChange, onChangedValue, onValueChange]);

  
  return (
    <div className="w-full mt-4">
      { loading && <p>불러오는 중...</p> }
      { error && <p style={{ color: "red" }}>{error}</p> }

      { !loading && !error && (
        <>
          <table className="w-full table-fixed border-collapse text-sm ">
            <tbody className="">
              <tr className="w-full">
                <td className="px-3 py-3 w-2/6 border border-gray-300">id</td>
                <td className="px-3 py-3 w-4/6 border border-gray-300">{data.id ? data.id : "-"}</td>
              </tr>
              <tr className="w-full">
                <td className="px-3 py-3 w-2/6 border border-gray-300">username</td>
                <td className="px-3 py-3 w-4/6 border border-gray-300">{data.username ? data.username : "-"}</td>
              </tr>
              <tr className="w-full">
                <td className="px-3 py-3 w-2/6 border border-gray-300">email</td>
                <td className="px-3 py-3 w-4/6 border border-gray-300">{data.email ? data.email : "-"}</td>
              </tr>
              <tr className="w-full">
                <td className="px-3 py-3 w-2/6 border border-gray-300">role</td>
                <td className="px-3 py-3 w-4/6 border border-gray-300">
                  <select 
                    name="roles" 
                    id="roles" 
                    value={selectedRoleOpt}
                    onChange={handleRoleChange}
                    className="border-1 outline-none px-0.5 pb-0.5 text-sm"
                  >
                    <option value={data.role || ""}>{data.role ? data.role : "-"}</option>
                    {availableRoleOpt.map((option, index) => (
                      <option key={index} value={option}>
                        {option}
                      </option>
                    ))}
                  </select>
                </td>
              </tr>
              <tr className="w-full">
                <td className="px-3 py-3 w-2/6 border border-gray-300">status</td>
                <td className="px-3 py-3 w-4/6 border border-gray-300">
                  <select 
                    name="roles" 
                    id="roles" 
                    value={selectedStatusOpt}
                    onChange={handleStatusChange}
                    className="border-1 outline-none px-0.5 pb-0.5 text-sm"
                  >
                    <option value={data.status || ""}>{data.status ? data.status : "-"}</option>
                    {availableStatusOpt.map((option, index) => (
                      <option key={index} value={option}>
                        {option}
                      </option>
                    ))}
                  </select>
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
  );
}

export default AdminUserDetailPage;