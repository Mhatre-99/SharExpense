import './App.css';
import React from 'react';
import {Routes, Route} from 'react-router-dom';
import Authentication from "./Components/Authentication/Authentication";
import Protected from "./Components/Protected/Protected";
import NotFound from "./Components/Protected/NotFound";
function App() {
  return (
      <div>
       <Routes>
           <Route path="/login" element={<Authentication/>}></Route>
           <Route path="/signup" element={<Authentication/>}></Route>
           <Route path="/access/*" element={<Protected/>}></Route>
           <Route path="*" element={<NotFound />}/>
       </Routes>


      </div>
  );
}

export default App;
