import React, { useEffect, useState } from 'react';
import { listUsers, updateUserRole, deleteUser } from '../services/UserService';

const ListUsersComponent = () => {
    const [users, setUsers] = useState([]);
    const [editingRole, setEditingRole] = useState(null);
    const [newRole, setNewRole] = useState('');

    useEffect(() => {
        loadUsers();
    }, []);

    const loadUsers = () => {
        listUsers().then(response => setUsers(response.data)).catch(error => console.error(error));
    };

    const handleRoleUpdate = (userId) => {
        updateUserRole(userId, newRole)
            .then(() => {
                setEditingRole(null);
                loadUsers();
            })
            .catch(error => console.error(error));
    };

    const handleDeleteUser = (userId) => {
        deleteUser(userId).then(() => loadUsers()).catch(error => console.error(error));
    };

    return (
        <div className='container'>
            <h1 className='text-center'>List of Users</h1>
            <table className='table table-striped table-bordered'>
                <thead>
                    <tr>
                        <th>User ID</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {users.map(user => (
                        <tr key={user.id}>
                            <td>{user.id}</td>
                            <td>{user.username}</td>
                            <td>{user.email}</td>
                            <td>
                                {editingRole === user.id ? (
                                    <>
                                        <select value={newRole} onChange={(e) => setNewRole(e.target.value)}>
                                            <option value="USER">USER</option>
                                            <option value="ADMIN">ADMIN</option>
                                        </select>
                                        <button className="btn btn-success" onClick={() => handleRoleUpdate(user.id)}>Save</button>
                                    </>
                                ) : (
                                    <>
                                        {user.role}
                                        <button className="btn btn-info" onClick={() => setEditingRole(user.id)}>Edit</button>
                                    </>
                                )}
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

export default ListUsersComponent;
