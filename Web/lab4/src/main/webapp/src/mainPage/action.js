// actions.js
export const drawGraph = (r) => ({
  type: 'DRAW_GRAPH',
  payload: { r },
});

export const drawDots = (dots) => ({
  type: 'DRAW_DOTS',
  payload: { dots },
});
