import React from 'react';
import { useNavigate } from 'react-router-dom';

const HeaderComponent = ({ isAuthenticated, setIsAuthenticated }) => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('token');
    setIsAuthenticated(false);
    navigate('/login');
  };

  return (
    <header className="header">
      <nav className="navbar navbar-dark bg-dark">
        <a className="navbar-brand" href="/">Employee Management System</a>
        <div>
          {isAuthenticated ? (
            <button className="btn btn-danger" onClick={handleLogout}>Logout</button>
          ) : (
            <button className="btn btn-primary" onClick={() => navigate('/login')}>Login</button>
          )}
        </div>
      </nav>
    </header>
  );
};

export default HeaderComponent;
