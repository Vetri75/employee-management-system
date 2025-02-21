import { useState, useEffect } from 'react';
import { BrowserRouter, Route, Routes, Navigate } from 'react-router-dom';
import ListEmployeeComponent from './components/ListEmployeeComponent';
import EmployeeComponent from './components/EmployeeComponent';
import HeaderComponent from './components/HeaderComponent';
import FooterComponent from './components/FooterComponent';
import LoginComponent from './components/LoginComponent';
import ListUsersComponent from './components/ListUsersComponent';

<Routes>
  {role === 'ADMIN' && <Route path='/users' element={<ListUsersComponent />} />}
</Routes>


const ProtectedRoute = ({ element, ...rest }) => {
  const token = localStorage.getItem('token');
  return token ? element : <Navigate to="/login" />;
};

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(!!localStorage.getItem('token'));

  useEffect(() => {
    const handleStorageChange = () => setIsAuthenticated(!!localStorage.getItem('token'));
    window.addEventListener('storage', handleStorageChange);
    return () => window.removeEventListener('storage', handleStorageChange);
  }, []);

  return (
    <BrowserRouter>
      <HeaderComponent isAuthenticated={isAuthenticated} setIsAuthenticated={setIsAuthenticated} />
      <Routes>
        <Route path="/login" element={<LoginComponent setIsAuthenticated={setIsAuthenticated} />} />
        <Route path="/" element={<ProtectedRoute element={<ListEmployeeComponent />} />} />
        <Route path="/employees" element={<ProtectedRoute element={<ListEmployeeComponent />} />} />
        <Route path="/add-employee" element={<ProtectedRoute element={<EmployeeComponent />} />} />
        <Route path="/edit-employee/:id" element={<ProtectedRoute element={<EmployeeComponent />} />} />
        <Route path="/login" element={<LoginComponent />} />
        <Route path="/employees" element={<PrivateRoute><ListEmployeeComponent /></PrivateRoute>} />
        <Route path="/users" element={<PrivateRoute><ListUsersComponent /></PrivateRoute>} />
        <Routes>{role === 'ADMIN' && <Route path='/users' element={<ListUsersComponent />} />}</Routes>
      </Routes>
      <FooterComponent />
    </BrowserRouter>
  );
}

export default App;
