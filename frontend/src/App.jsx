import api from './api/axios';
import { Routes, Route } from 'react-router';
import { useState, useEffect } from 'react';
import { HomePage } from './pages/home/HomePage';
import { CheckoutPage } from './pages/checkout/CheckoutPage';
import { OrdersPage } from './pages/orders/OrdersPage';
import { LoginPage } from './pages/auth/LoginPage';
import { RegisterPage } from './pages/auth/RegisterPage';
import { TrackingPage } from './pages/tracking/TrackingPage';
import './App.css';

function App() {
  const [cart, setCart] = useState([]);

  const loadCart = async () => {
    try {
      const response = await api.get("/cart");
      setCart(response.data);
    } catch (error) {
      console.error(error);

      if (error.response?.status === 401) {
        setCart([]);
      }
    }
  };

  useEffect(() => {
    loadCart();
  }, []);

  return (
    <Routes>
      <Route
        index
        element={<HomePage cart={cart} loadCart={loadCart} />}
      />

      <Route
        path="checkout"
        element={<CheckoutPage cart={cart} loadCart={loadCart} />}
      />

      <Route
        path="orders"
        element={<OrdersPage cart={cart} loadCart={loadCart} />}
      />

      <Route
        path="/login"
        element={<LoginPage />}
      />

      <Route
        path="/register"
        element={<RegisterPage />}
      />

      <Route
        path="tracking/:orderId/:productId"
        element={<TrackingPage cart={cart} />}
      />
    </Routes>
  );
}

export default App;