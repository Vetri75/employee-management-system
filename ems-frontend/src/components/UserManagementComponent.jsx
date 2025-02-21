import React, { useEffect, useState } from 'react';
import { listUsers, updateUserRole, deleteUser } from '../services/UserService';
import { useNavigate } from 'react-router-dom';

const UserManagementComponent = () => {
    const [users, setUsers] = useState([]);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        fetchUsers();
    }, []);

    const fetchUsers = () => {
        listUsers().then(response => {
            setUsers(response.data);
        }).catch(error => {
            setError("Failed to load users");
        });
    };

    const handleRoleChange = (userId, newRole) => {
        updateUserRole(userId, newRole).then(() => {
            fetchUsers();
        }).catch(() => {
            setError("Failed to update role");
        });
    };

    const handleDeleteUser = (userId) => {
        deleteUser(userId).then(() => {
            fetchUsers();
        }).catch(() => {
            setError("Failed to delete user");
        });
    };

    return (
        <div className='container'>
            <h2 className='text-center'>User Management</h2>
            {error && <div className='alert alert-danger'>{error}</div>}
            <table className='table table-striped'>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Role</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {users.map(user => (
                        <tr key={user.id}>
                            <td>{user.id}</td>
                            <td>{user.username}</td>
                            <td>
                                <select value={user.role} onChange={(e) => handleRoleChange(user.id, e.target.value)}>
                                    <option value='USER'>USER</option>
                                    <option value='ADMIN'>ADMIN</option>
                                </select>
                            </td>
                            <td>
                                <button className='btn btn-danger' onClick={() => handleDeleteUser(user.id)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default UserManagementComponent;
