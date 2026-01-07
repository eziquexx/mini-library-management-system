import axios from "axios";
import { useEffect, useState } from "react";
import CustomPagination from "./CustomPagination";

const CustomTable = ({apiUrl, sendData, onRowClick}) => {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(15);
  const [posts, setPosts] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  const fetchPost = async (page, size) => {
    setLoading(true);
    setError(null);

    try {
      let response;

      response = await axios.get(
        `${apiUrl}/users`,
        {
          params: {
            page: page,
            size: size,
          },
          withCredentials: true,
          headers: {
            Aceept: "application/json",
          }
        }
      );

      setPosts(response.data.data.content);
      setTotalPages(response.data.data.totalPages || 1);
      setTotalElements(response.data.data.totalElements || 1);
      sendTotalElements(totalElements);
    } catch (error) {
      setError(error);
      console.log("Error: ", error);
    } finally {
      setLoading(false);
    }
  }

  const sendTotalElements = (totalElements) => {
    sendData(totalElements);
  }

  const handleDataPage = (thisPage) => {
    setPage(thisPage);
  }

  const handlePageClick = (pageNumber) => {
    setPage(pageNumber);
  }
  

  useEffect(() => {
    fetchPost(page, size).then(() => { 
      sendTotalElements(totalElements); // 비동기 요청 후 부모에게 데이터 전달
    });
  }, [page, size, totalElements]);

  let content;
  if (!loading && !error) {
    if (posts && posts.length > 0) {
      content = (
        <tbody>
          {posts.map((post) => (
            <tr 
              key={post.id} 
              className="border-b-1 border-gray-300 hover:bg-gray-200 cursor-pointer" 
              onClick={() => onRowClick(post.id)}
            >
              <td className="w-1/22 px-2 py-4 mx-[2px] overflow-hidden">
                <div className="custom-scrollbar">{post.id}</div>
              </td>
              <td className="w-3/22 px-2 py-4 mx-[2px] overflow-hidden">
                <div className="custom-scrollbar">{post.username}</div>
              </td>
              <td className="w-2/22 px-2 py-4 mx-[2px] overflow-hidden">
                <div className="custom-scrollbar">{post.email}</div>
              </td>
              <td className="w-1/22 px-2 py-4 mx-[2px] overflow-hidden">
                <div className="custom-scrollbar">{post.role}</div>
              </td>
              <td className="w-1/22 px-2 py-4 mx-[2px] overflow-hidden">
                <div className="custom-scrollbar">{post.joinDate}</div>
              </td>
              <td className="w-1/22 px-2 py-4 mx-[2px] overflow-hidden">
                <div className="custom-scrollbar">last_login_date</div>
              </td>
              <td className="w-1/22 px-2 py-4 mx-[2px] overflow-hidden">
                <div className="custom-scrollbar">updated_at</div>
              </td>
              <td className="w-1/22 px-2 py-4 mx-[2px] overflow-hidden">
                <div className="custom-scrollbar">inactive_at</div>
              </td>
              <td className="w-1/22 px-2 py-4 mx-[2px] overflow-hidden">
                <div className="custom-scrollbar">deleted_at</div>
              </td>
              <td className="w-1/22 px-2 py-4 mx-[2px] overflow-hidden">
                <div className="custom-scrollbar">status</div>
              </td>
            </tr>
          ))}
        </tbody>
      );
    }
  } else {
    content = (
      <tbody>
        <tr>
          <td>등록된 게시물이 없습니다.</td>
        </tr>
      </tbody>
    )
  }

  return (
    <div className="flex flex-col justify-center">
      <table className="
          table-fixed border-collapse
          text-xs w-full h-full min-w-[1280px] "
        >
        <thead className="w-full">
          <tr className="w-full">
            <th className="w-1/22">
              <div className="bg-[#f2f3f7] py-2.5 mr-[2px]">id</div>
            </th>
            <th className="w-3/22">
              <div className="bg-[#f2f3f7] p-2.5 mx-[2px]">username</div>
            </th>
            <th className="w-4/22">
              <div className="bg-[#f2f3f7] py-2.5 mx-[2px] ">email</div>
            </th>
            <th className="w-2/22">
              <div className="bg-[#f2f3f7] py-2.5 mx-[2px]">role</div>
            </th>
            <th className="w-3/22">
              <div className="bg-[#f2f3f7] py-2.5 mx-[2px]">join_date</div>
            </th>
            <th className="w-3/22">
              <div className="bg-[#f2f3f7] py-2.5 mx-[2px]">last_login_date</div>
            </th>
            <th className="w-3/22">
              <div className="bg-[#f2f3f7] py-2.5 mx-[2px]">updated_at</div>
            </th>
            <th className="w-3/22">
              <div className="bg-[#f2f3f7] py-2.5 mx-[2px]">inactive_at</div>
            </th>
            <th className="w-3/22">
              <div className="bg-[#f2f3f7] py-2.5 mx-[2px]">deleted_at</div>
            </th>
            <th className="w-2/22">
              <div className="bg-[#f2f3f7] py-2.5 ml-[2px]">status</div>
            </th>
          </tr>
        </thead>
        {/* tbody 부분 */}
        { content }        
      </table>

      <div className="flex relative mt-4 h-10">
        <div className="flex items-center text-sm pl-3 text-gray-600">
          <span className="mr-1">{page+1}</span>
          <span className="mr-1">/</span>
          <span className="mr-1">{totalPages}</span>
          <span>페이지</span>
        </div>
        {/* 페이징 */}
        <CustomPagination page={page} totalPages={totalPages} handlePageClick={handlePageClick} sendDataPage={handleDataPage}/>
      </div>
    </div>
  );
}

export default CustomTable;