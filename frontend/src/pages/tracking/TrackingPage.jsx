import { useParams, Link } from "react-router";
import { useEffect, useState } from "react";
import dayjs from "dayjs";
import api from "../../api/axios";
import { Header } from "../../components/Header";
import "./TrackingPage.css";

export function TrackingPage({ cart }) {

    const { orderId, productId } = useParams();

    const [tracking, setTracking] = useState(null);

    useEffect(() => {

        api.get(`/orders/${orderId}/items/${productId}`)
            .then((response) => {
                setTracking(response.data);
            });

    }, [orderId, productId]);

    

    if (!tracking) {
        return <div>Loading...</div>;
    }
    const progress =
    tracking.status === "PENDING" || tracking.status === "CONFIRMED"
        ? "33%"
        : tracking.status === "SHIPPED"
        ? "66%"
        : "100%";

    return (
        <>
            <Header cart={cart} />

            <div className="tracking-page">

                <div className="order-tracking">

                    <Link
                        to="/orders"
                        className="back-to-orders-link link-primary"
                    >
                        View all orders
                    </Link>

                    <div className="delivery-date">
                        Arriving on{" "}
                        {dayjs(tracking.estimatedDeliveryDate).format(
                            "dddd, MMMM D"
                        )}
                    </div>

                    <div className="product-info">
                        {tracking.product.name}
                    </div>

                    <div className="product-info">
                        Quantity: {tracking.quantity}
                    </div>

                    <img
    className="product-image"
    src={`${tracking.product.image}`}
    alt={tracking.product.name}
/>

                    <div className="progress-labels-container">

                        <div
                            className={`progress-label ${
                                tracking.status === "PENDING"
                                    ? "current-status"
                                    : ""
                            }`}
                        >
                            Preparing
                        </div>

                        <div
                            className={`progress-label ${
                                tracking.status === "SHIPPED"
                                    ? "current-status"
                                    : ""
                            }`}
                        >
                            Shipped
                        </div>

                        <div
                            className={`progress-label ${
                                tracking.status === "DELIVERED"
                                    ? "current-status"
                                    : ""
                            }`}
                        >
                            Delivered
                        </div>

                    </div>

                    <div className="progress-bar-container">

                        <div
                            className="progress-bar"
                            style={{ width: progress }}
                        />

                    </div>

                </div>

            </div>
        </>
    );
}