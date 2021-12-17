import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import TileNftApp from './TileNftApp';
import reportWebVitals from './etc/reportWebVitals';

ReactDOM.render(
  <React.StrictMode>
    <TileNftApp />
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
