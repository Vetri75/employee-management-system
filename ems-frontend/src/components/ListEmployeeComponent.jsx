import React, { useEffect, useState } from 'react';
import { deleteEmployee, ListEmployees } from '../services/EmployeeService';
import { useNavigate } from 'react-router-dom';

function ListEmployeeComponent() {
    const [employees, setEmployees] = useState([]);
    const [userRole, setUserRole] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        getAllEmployees();
        checkUserRole();
    }, []);

    function getAllEmployees() {
        ListEmployees().then((response) => {
            setEmployees(response.data);
        }).catch(error => {
            console.error(error);
        });
    }

    function checkUserRole() {
        const token = localStorage.getItem('token');
        if (token) {
            const decodedToken = jwtDecode(token);
            setUserRole(decodedToken.role);
        }
    }

    function addNewEmployee() {
        if (userRole === 'ADMIN') {
            navigate('/add-employee');
        }
    }

    function updateEmployee(id) {
        navigate(`/edit-employee/${id}`);
    }

    function removeEmployee(id) {
        if (userRole === 'ADMIN') {
            deleteEmployee(id).then(() => {
                getAllEmployees();
            }).catch(error => {
                console.log(error);
            });
        }
    }

    return (
        <div className='container'>
            <h1 className='text-center'>List of Employees</h1>
            {userRole === 'ADMIN' && (
                <button type='button' className='btn btn-outline-primary' onClick={addNewEmployee}>Add Employee</button>
            )}
            <table className='table table-striped table-bordered'>
                <thead>
                    <tr>
                        <th>Employee Id</th>
                        <th>Employee First Name</th>
                        <th>Employee Last Name</th>
                        <th>Employee Email Id</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {employees.map(employee => (
                        <tr key={employee.id}>
                            <td>{employee.id}</td>
                            <td>{employee.firstName}</td>
                            <td>{employee.lastName}</td>
                            <td>{employee.email}</td>
                            <td>
                                <button className='btn btn-info' onClick={() => updateEmployee(employee.id)}>Update</button>
                                {userRole === 'ADMIN' && (
                                    <button className='btn btn-danger' onClick={() => removeEmployee(employee.id)} style={{ marginLeft: '10px' }}>Delete</button>
                                )}
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default ListEmployeeComponent;
