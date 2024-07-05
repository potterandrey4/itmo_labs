// store.js
import { createStore } from 'redux';
import appReducer from './reducers.js';

const store = createStore(appReducer);

export default store;
