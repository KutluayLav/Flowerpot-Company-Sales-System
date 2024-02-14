import * as React from "react";
import * as ReactDOM from "react-dom/client";
import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";
import "./index.css";
import { Login } from "./pages/Login";
import Register from "./pages/Register";
import { HomeScreen } from "./pages/Home";
import Products from "./pages/Products";
import Users from "./pages/Users";
import Profile from "./pages/Profile";
import AddProduct from "./component/AddProduct";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Login/>,
  },
  {
    path: "/login",
    element: <Login/>,
  },
  {
    path: "/register",
    element: <Register/>,
  },
  {
    path:"/home",
    element:<HomeScreen/>
  },
  {
    path:"/products",
    element:<Products/>
  },
  {
    path:"/users",
    element:<Users/>
  },
  {
    path:"/profile",
    element:<Profile/>
  },
  {
    path:"/add-product",
    element:<AddProduct/>
  }
]);

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <RouterProvider router={router}/>
  </React.StrictMode>
);