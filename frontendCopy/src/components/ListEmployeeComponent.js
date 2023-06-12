/* eslint-disable jsx-a11y/img-redundant-alt */
import React, { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import EmployeeService from '../services/EmployeeService'

const ListEmployeeComponent = () => {

    const [employees, setEmployees] = useState([])

    useEffect(() => {
        getAllEmployees();
    }, [])

    const getAllEmployees = () => {
        EmployeeService.getAllEmployees().then((response) => {
            setEmployees(response.data.data)
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }

    // const deleteEmployee = (employeeId) => {
    //    EmployeeService.deleteEmployee(employeeId).then((response) =>{
    //     getAllEmployees();

    //    }).catch(error =>{
    //        console.log(error);
    //    })

    // }

    return (
        <div className="container">
            <h2 className="text-center"> List Employees </h2>
            <Link to="/add-employee" className="btn btn-primary mb-2" > Add Employee </Link>
            <table className="table table-bordered table-striped">
                <thead>
                    <tr>
                        <th> Employee Id </th>
                        <th> Employee Avatar </th>
                        <th> Employee First Name </th>
                        <th> Employee Last Name </th>
                        <th> Employee Email Id </th>
                        <th> Actions </th>
                    </tr>
                </thead>
                <tbody>
                    {
                        employees.map(
                            employee =>
                                <tr key={employee.id}>
                                    <td> {employee.id} </td>
                                    <td> <img src={employee.avatar} alt="Image description" style={{width: '35px', height: '35px', borderRadius: '50%'}} /> </td>
                                    <td> {employee.firstName} </td>
                                    <td>{employee.lastName}</td>
                                    <td>{employee.address}</td>
                                    <td>
                                        abc
                                        {/* <Link className="btn btn-info" to={`/edit-employee/${employee.id}`} >Update</Link> */}
                                        {/* <button className = "btn btn-danger" onClick = {() => deleteEmployee(employee.id)}
                                    style = {{marginLeft:"10px"}}> Delete</button> */}
                                    </td>
                                </tr>
                        )
                    }
                </tbody>
            </table>
        </div>
    )
}

export default ListEmployeeComponent
