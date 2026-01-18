import axios from "axios";
import { useEffect, useState } from "react";


const AdminUserDetailPage = ({item, apiUrl}) => {

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [data, setData] = useState([]);
  const id = item;

  useEffect(() => {
    fetchData();
  }, [item])

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

      console.log(response.data.data);
      setData(response.data.data);

    } catch (error) {
      setError(error);
      console.log(error);
    } finally {
      setLoading(false);
    }
  }
  

  return (
    <div className="w-full mt-4">
      { loading && <p>불러오는 중...</p> }
      { error && <p style={{ color: "red" }}>{error}</p> }

      { !loading && !error && (
        <>
          <table className="w-full table-fixed border-collapse text-sm ">
            <tbody className="">
              <tr className="w-full">
                <td className="px-2.5 py-1.5 w-2/6 border border-gray-300">id</td>
                <td className="px-2.5 py-1.5 w-4/6 border border-gray-300">{data.id ? data.id : "-"}</td>
              </tr>
              <tr className="w-full">
                <td className="px-2.5 py-1.5 w-2/6 border border-gray-300">username</td>
                <td className="px-2.5 py-1.5 w-4/6 border border-gray-300">{data.username ? data.username : "-"}</td>
              </tr>
              <tr className="w-full">
                <td className="px-2.5 py-1.5 w-2/6 border border-gray-300">email</td>
                <td className="px-2.5 py-1.5 w-4/6 border border-gray-300">{data.email ? data.email : "-"}</td>
              </tr>
              <tr className="w-full">
                <td className="px-2.5 py-1.5 w-2/6 border border-gray-300">role</td>
                <td className="px-2.5 py-1.5 w-4/6 border border-gray-300">{data.role ? data.role : "-"}</td>
              </tr>
              <tr className="w-full">
                <td className="px-2.5 py-1.5 w-2/6 border border-gray-300">status</td>
                <td className="px-2.5 py-1.5 w-4/6 border border-gray-300">{data.status ? data.status : "-"}</td>
              </tr>
              <tr className="w-full">
                <td className="px-2.5 py-1.5 w-2/6 border border-gray-300">join_date</td>
                <td className="px-2.5 py-1.5 w-4/6 border border-gray-300">{data.joinDate ? data.joinDate.split('T').join(' / ') : "-"}</td>
              </tr>
              <tr className="w-full">
                <td className="px-2.5 py-1.5 w-2/6 border border-gray-300">last_login_date</td>
                <td className="px-2.5 py-1.5 w-4/6 border border-gray-300">{data.lastLoginDate ? data.lastLoginDate.split('T').join(' / ') : "-"}</td>
              </tr>
              <tr className="w-full">
                <td className="px-2.5 py-1.5 w-2/6 border border-gray-300">updated_at</td>
                <td className="px-2.5 py-1.5 w-4/6 border border-gray-300">{data.updatedAt ? data.updatedAt.split('T').join(' / ') : "-"}</td>
              </tr>
              <tr className="w-full">
                <td className="px-2.5 py-1.5 w-2/6 border border-gray-300">inactive_at</td>
                <td className="px-2.5 py-1.5 w-4/6 border border-gray-300">{data.inactiveAt ? data.inactiveAt.split('T').join(' / ') : "-"}</td>
              </tr>
              <tr className="w-full">
                <td className="px-2.5 py-1.5 w-2/6 border border-gray-300">deleted_at</td>
                <td className="px-2.5 py-1.5 w-4/6 border border-gray-300">{data.deletedAt ? data.deletedAt.split('T').join(' / ') : "-"}</td>
              </tr>
            </tbody>
          </table>
        </>
      )}
    </div>
  );
}

export default AdminUserDetailPage;