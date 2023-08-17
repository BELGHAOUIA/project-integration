export default function authHeader() {
  let user = JSON.parse(localStorage.getItem('user'));

  if (user && user.jwt) {
    return 'Bearer ' + user.jwt ;
  } else {
    return '';
  }
}
