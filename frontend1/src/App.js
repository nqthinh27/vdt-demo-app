import './App.css';
import { Route, Routes } from 'react-router-dom';
import FooterComponent from './components/FooterComponent';
import HeaderComponent from './components/HeaderComponent';
import ListEmployeeComponent from './components/ListEmployeeComponent';
import AddEmployeeComponent from './components/AddEmployeeComponent';

function App() {
  return (
    <div>
        <HeaderComponent />
        {/* <div className="container"> */}
        <Routes>
          <Route path="/" element={<ListEmployeeComponent />} />
          {/* <Route path="/employees" element={<ListEmployeeComponent />} /> */}
          <Route path="/add-employee" element={<AddEmployeeComponent />} />
        </Routes>
          {/* <Route path="/edit-employee/:id" element={<AddEmployeeComponent />} /> */}
        {/* </div> */}
        <FooterComponent />
    </div>
  );
}

export default App;
