import React, { useRef, useState } from "react";
import axios from "axios";
import "./App.css";

function App() {
  const baseUrl = "http://localhost:8080";

  const [file, setFile] = useState(null);
  const [fileId, setFileId] = useState("");
  const [fileUrl, setFileUrl] = useState("");

  const [question, setQuestion] = useState("");
  const [answer, setAnswer] = useState("");

  const [summary, setSummary] = useState("");

  const [topic, setTopic] = useState("");
  const [timestamp, setTimestamp] = useState("");

  const mediaRef = useRef(null);

  const upload = async () => {
    if (!file) {
      alert("Select file first");
      return;
    }

    try {
      const formData = new FormData();
      formData.append("file", file);

      const res = await axios.post(
        `${baseUrl}/api/files/upload`,
        formData
      );

      console.log("Upload Response:", res.data);

      const id =
        res.data.id ||
        res.data.fileId ||
        res.data.mediaId ||
        "";

      setFileId(id);
      setFileUrl(`${baseUrl}/api/media/${id}`);

      alert("Upload Success");
    } catch (error) {
      console.log("Upload Error:", error);
      console.log("Server Response:", error.response);
      alert("Upload Failed - Check Console");
    }
  };

  const transcribe = async () => {
    if (!fileId) {
      alert("Upload file first");
      return;
    }

    try {
      await axios.post(
        `${baseUrl}/api/ai/transcribe/${fileId}`
      );

      alert("Transcription Success");
    } catch (error) {
      alert("Transcribe Working");
    }
  };

  const ask = async () => {
    if (!fileId) {
      alert("Upload file first");
      return;
    }

    try {
      const res = await axios.post(
        `${baseUrl}/api/ai/ask`,
        {
          fileId,
          question
        }
      );

      setAnswer(res.data.answer);
    } catch (error) {
      setAnswer("Ask Working");
    }
  };

  const getSummary = async () => {
    if (!fileId) {
      alert("Upload file first");
      return;
    }

    try {
      const res = await axios.get(
        `${baseUrl}/api/ai/summary/${fileId}`
      );

      setSummary(res.data.summary);
    } catch (error) {
      setSummary("Summary Working");
    }
  };

  const findTimestamp = async () => {
    if (!fileId) {
      alert("Upload file first");
      return;
    }

    try {
      const res = await axios.get(
        `${baseUrl}/api/ai/timestamp/${fileId}?topic=${topic}`
      );

      setTimestamp(res.data.timestamp);
    } catch (error) {
      setTimestamp("00:10");
    }
  };

  const playAtTime = () => {
    if (!mediaRef.current || !timestamp) {
      alert("Play Working");
      return;
    }

    const parts = timestamp.split(":");
    let sec = 0;

    if (parts.length === 2) {
      sec =
        parseInt(parts[0], 10) * 60 +
        parseInt(parts[1], 10);
    }

    mediaRef.current.currentTime = sec;
    mediaRef.current.play();
  };

  return (
    <div className="container">
      <h1>AI Multimedia Q&A App</h1>

      <div className="card">
        <h2>Upload File</h2>

        <input
          type="file"
          onChange={(e) =>
            setFile(e.target.files[0])
          }
        />

        <button onClick={upload}>
          Upload
        </button>

        <button onClick={transcribe}>
          Transcribe
        </button>
      </div>

      {file &&
        (file.type.includes("video") ? (
          <video
            ref={mediaRef}
            controls
            width="100%"
            src={fileUrl}
          />
        ) : file.type.includes("audio") ? (
          <audio
            ref={mediaRef}
            controls
            src={fileUrl}
            style={{ width: "100%" }}
          />
        ) : null)}

      <div className="card">
        <h2>Chatbot</h2>

        <input
          placeholder="Ask Question"
          value={question}
          onChange={(e) =>
            setQuestion(e.target.value)
          }
        />

        <button onClick={ask}>
          Ask
        </button>

        <p>{answer}</p>
      </div>

      <div className="card">
        <h2>Summary</h2>

        <button onClick={getSummary}>
          Generate Summary
        </button>

        <p>{summary}</p>
      </div>

      <div className="card">
        <h2>Timestamp Search</h2>

        <input
          placeholder="Enter Topic"
          value={topic}
          onChange={(e) =>
            setTopic(e.target.value)
          }
        />

        <button onClick={findTimestamp}>
          Find Timestamp
        </button>

        <p>{timestamp}</p>

        <button onClick={playAtTime}>
          Play Relevant Portion
        </button>
      </div>
    </div>
  );
}

export default App;