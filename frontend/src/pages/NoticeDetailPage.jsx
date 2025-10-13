import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";


const NoticeDetailPage = () => {

  const { noticeId } = useParams();
  const [post, setPost] = useState([]);
  const [loading, setLoanding] = useState(true);
  const [error, setError] = useState(null);

  const fetchPost = async (noticeId) => {
    setLoanding(true);
    setError(null);

    try {
      const response = await axios.get(
        `http://localhost:8080/api/v1/notices/${noticeId}`,
        {
          withCredentials: true,
          headers: {
            Accept: "application/json",
          },
        }
      );

      setPost(response.data.data);
      
      console.log(response.data.data);

    } catch (error) {
      console.error("Error: ", error);
      setError("데이터를 불러오지 못했습니다.");
    } finally {
      setLoanding(false);
    }
  }

  useEffect(() => {
    if (noticeId) {
      fetchPost(noticeId);
    }
  }, [noticeId]);

  return (
    <>
      <div>
        { loading && <p>불러오는 중...</p> }
        { error && <p style={{ color: "red" }}>{error}</p> }

        <p>{post.id}</p>
        <p>{post.writer && post.writer.username}</p>
        <p>{post.createdDate}</p>
        <p>{post.title}</p>
        <p>{post.content}</p>
      </div>
    </>
  );
}

export default NoticeDetailPage;