// reducers.js
const initialState = {
  r: null,
  x: null,
  y: null,
  dots: [], // Массив точек
};

const appReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'DRAW_GRAPH':
      return { ...state, r: action.payload.r };
    case 'DRAW_DOTS':
      return { ...state, dots: action.payload.dots };
    default:
      return state;
  }
};

export default appReducer;
