import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { login, logout } from './actions';

function AuthComponent() {
  const user = useSelector((state) => state.user);
  const dispatch = useDispatch();

  const handleLogin = () => {
    // Ваша логика входа
    dispatch(login({ username: 'старый пользователь' }));
    alert('Выполнен вход');
  };login

  const handleSignin = () => {
    // Ваша логика регистрации
    dispatch(login({ username: 'новый пользователь' }));
    alert('Выполнена регистрация');
  };

  const handleLogout = () => {
    // Ваша логика выхода
    dispatch(logout());
    alert('Выполнен выход');
  };

  return (
    <>
      {user ? (
        <div>
          <p>Привет, {user.username}!</p>
          <button onClick={handleLogout}>Выйти</button>
        </div>
      ) : (
        <div>
          <p>Вход:</p>
          <button onClick={handleLogin}>Войти</button>
          <br></br>
          <p>Регистрация:</p>
          <button onClick={handleSignin}>Войти</button>
        </div>
      )}
    </>
  );
}

export default AuthComponent;