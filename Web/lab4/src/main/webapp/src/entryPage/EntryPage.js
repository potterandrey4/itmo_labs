// EntryPage.js
import React from 'react';
import { Provider } from 'react-redux';
import { store } from './store';
import AuthComponent from './AuthComponent';

const EntryPage = () => {
  return (
    <Provider store={store}>
      <AuthComponent />
    </Provider>
  );
};

export default EntryPage;
