import React from 'react';
import ReactDOM from 'react-dom';
import { createRoot } from 'react-dom/client';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { redirect } from "react-router-dom";
import MainPage from './mainPage/MainPage';
import EntryPage from './entryPage/EntryPage';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/entry" element={<EntryPage />} />
      </Routes>
    </Router>
  );
};

const appRoot = document.getElementById('root');
const entryBtnsRoot = document.getElementById('entryBtns');

createRoot(appRoot).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);


// Обработчик для кнопки выхода
const handleLogout = () => {
  alert('вы вышли');
  // редирект буду делать на бэке
};

// Условный рендеринг для элемента entryBtns в зависимости от пути
const renderEntryBtns = () => {
  if (window.location.pathname === '/') {
    ReactDOM.render(
      <button onClick={handleLogout}>Logout</button>,
      entryBtnsRoot
    );
  } else {
    ReactDOM.render(null, entryBtnsRoot);
  }
};

// Вызов функции в начале и после каждого обновления пути
renderEntryBtns();
window.addEventListener('popstate', renderEntryBtns);
