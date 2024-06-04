export const login = (userData) => ({
  type: 'LOGIN',
  payload: userData,
});

export const signin = (userData) => ({
  type: 'SIGNIN',
  payload: userData,
});

export const logout = () => ({
  type: 'LOGOUT',
});