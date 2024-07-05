import React, { useState } from 'react';

export default function SubmitButton({ onSubmit }) {
  return (
	<button type="button" onClick={onSubmit}>
	  Отправить
	</button>
  );
}