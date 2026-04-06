import { useState } from 'react';
import axios from 'axios';
import './App.css';

export default function App() {
  const [longUrl, setLongUrl]      = useState('');
  const [shortName, setShortName]  = useState('');
  const [result, setResult]        = useState(null);
  const [error, setError]          = useState('');
  const [loading, setLoading]      = useState(false);

  async function handleShorten(e) {
    // Prevent the page from reloading if using a <form>
    if (e) e.preventDefault();
    
    setError('');
    setResult(null);

    // Basic validation
    if (!longUrl.trim()) { setError('Please enter a long URL.'); return; }
    if (!shortName.trim()) { setError('Please enter a preferred short name.'); return; }

    try {
      setLoading(true);
      const response = await axios.post('http://localhost:8080/short-url', {
        longUrl:  longUrl,
        name:     shortName
      });
      setResult(response.data);
      setLongUrl('');
      setShortName('');
    } catch (err) {
      setError(err.response?.data?.message || 'Something went wrong.');
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="page">
      <div className="container">

        <header className="header">
          <div className="status-badge">
            <span className="status-dot" />
            <span className="status-text">URL SHORTENER</span>
          </div>
          <h1 className="title">
            Shrink It<span className="title-accent">.</span>
          </h1>
          <p className="subtitle">Turn long URLs into something beautiful</p>
        </header>

        {/* Wrapping in a form allows "Enter" key submission */}
        <form className="form-card" onSubmit={handleShorten}>

          <div className="input-group">
            <label className="input-label" htmlFor="longUrl">LONG URL</label>
            <div className="input-wrapper">
              <span className="input-icon">🔗</span>
              <input
                id="longUrl"
                className="text-input"
                type="url"
                placeholder="https://your-very-long-url.com/goes/here"
                value={longUrl}
                onChange={e => setLongUrl(e.target.value)}
              />
            </div>
          </div>

          <div className="input-group">
            <label className="input-label" htmlFor="shortName">PREFERRED SHORT TEXT</label>
            <div className="input-wrapper">
              <span className="domain-prefix">shrink.it/</span>
              <input
                id="shortName"
                className="text-input"
                type="text"
                placeholder="my-preferred-name"
                value={shortName}
                onChange={e => setShortName(e.target.value)}
              />
            </div>
          </div>

          <button type="submit" className="submit-button" disabled={loading}>
            {loading ? 'Shortening...' : 'Shorten URL ✦'}
          </button>

        </form>

        {/* Error message */}
        {error && (
          <div className="error-box">
            <p className="error-text">{error}</p>
          </div>
        )}

        {/* Success result - Fixed the <a> tag here */}
        {result && (
          <div className="result-box">
            <p className="result-label">YOUR SHORT URL</p>
            <a 
              className="result-url"
              href={result.shortUrl}
              target="_blank"
              rel="noreferrer"
            >
              {result.shortUrl}
            </a>
            <p className="result-message">{result.message}</p>
          </div>
        )}

        <p className="footer-text">SHRINK IT — URL SHORTENER</p>
      </div>
    </div>
  );
}