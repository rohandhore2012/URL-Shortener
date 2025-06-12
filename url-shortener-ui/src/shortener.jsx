import { useState } from 'react';
import axios from 'axios';

function Shortener() {
  const [url, setUrl] = useState('');
  const [expiry, setExpiry] = useState(60);
  const [shortUrl, setShortUrl] = useState('');
  const [error, setError] = useState('');

  const handleShorten = async () => {
    try {
      const res = await axios.post('http://localhost:8080/api/shorten', {
        url,
        expiry
      });
      setShortUrl(res.data.shortUrl);
      setError('');
    } catch (err) {
      setError('Something went wrong!');
      setShortUrl('');
    }
  };

  return (
    <div style={{ textAlign: 'center', paddingTop: '5rem' }}>
      <h1>🔗 URL Shortener</h1>
      <input
        type="text"
        placeholder="Enter long URL"
        value={url}
        onChange={(e) => setUrl(e.target.value)}
        style={{ width: '300px', margin: '10px' }}
      />
      <br />
      <input
        type="number"
        value={expiry}
        onChange={(e) => setExpiry(e.target.value)}
        placeholder="Expiry in minutes"
        style={{ width: '150px', margin: '10px' }}
      />
      <br />
      <button onClick={handleShorten}>Shorten</button>
      <br /><br />
      {shortUrl && <p><strong>Short URL:</strong> <a href={shortUrl} target="_blank">{shortUrl}</a></p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </div>
  );
}

export default Shortener;
