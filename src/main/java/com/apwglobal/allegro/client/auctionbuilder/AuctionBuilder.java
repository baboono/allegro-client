package com.apwglobal.allegro.client.auctionbuilder;

import com.apwglobal.nice.domain.NewAuctionField;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.apwglobal.allegro.client.auctionbuilder.Duration.UNLIMITED;
import static com.apwglobal.allegro.client.auctionbuilder.SellType.SHOP;
import static com.apwglobal.allegro.client.auctionbuilder.SendingTime.IN24H;
import static com.apwglobal.nice.domain.FieldType.Type;

public class AuctionBuilder {

    private String title;
    private int category;
    private Duration duration = UNLIMITED;
    private SellType sellType = SHOP;
    private int qty = 1;
    private Units units = Units.PCS;

    private Double price;
    private String image;
    private String desc;

    private String city;
    private String zip;
    private State state;
    private int country = 1;

    private Invoice invoice = Invoice.YES;
    private TransportPaidBy transportPaidBy = TransportPaidBy.BUYER;
    private Double priceForLetter;
    private Double priceForNextItemInLetter;
    private Integer qtyInLetter;

    private Double priceForCourier;
    private Double priceForNextItemInCourier;
    private Integer qtyInCourier;

    private SendingTime sendingTime = IN24H;
    private Color color;

    public AuctionBuilder title(String title) {
        this.title = title;
        return this;
    }

    public AuctionBuilder category(int category) {
        this.category = category;
        return this;
    }

    public AuctionBuilder duration(Duration duration) {
        this.duration = duration;
        return this;
    }

    public AuctionBuilder sellType(SellType sellType) {
        this.sellType = sellType;
        return this;
    }

    public AuctionBuilder qty(int qty) {
        this.qty = qty;
        return this;
    }

    public AuctionBuilder units(Units units) {
        this.units = units;
        return this;
    }

    public AuctionBuilder price(Double price) {
        this.price = price;
        return this;
    }

    public AuctionBuilder image(Path path) {
        try {
            InputStream is = FileUtils.openInputStream(path.toFile());
            this.image = Base64.getEncoder().encodeToString(IOUtils.toByteArray(is));
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

        return this;
    }

    public AuctionBuilder image(URI uri) {
        try {
            this.image = Base64.getEncoder().encodeToString(IOUtils.toByteArray(uri));
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

        return this;
    }

    public AuctionBuilder desc(String desc) {
        this.desc = desc;
        return this;
    }

    public AuctionBuilder city(String city) {
        this.city = city;
        return this;
    }

    public AuctionBuilder zip(String zip) {
        this.zip = zip;
        return this;
    }

    public AuctionBuilder state(State state) {
        this.state = state;
        return this;
    }

    public AuctionBuilder country(int country) {
        this.country = country;
        return this;
    }

    public AuctionBuilder invoice(Invoice invoice) {
        this.invoice = invoice;
        return this;
    }

    public AuctionBuilder transportPaidBy(TransportPaidBy transportPaidBy) {
        this.transportPaidBy = transportPaidBy;
        return this;
    }

    public AuctionBuilder priceForLetter(Double priceForLetter) {
        this.priceForLetter = priceForLetter;
        return this;
    }

    public AuctionBuilder priceForNextItemInLetter(Double priceForNextItemInLetter) {
        this.priceForNextItemInLetter = priceForNextItemInLetter;
        return this;
    }

    public AuctionBuilder qtyInLetter(Integer qtyInLetter) {
        this.qtyInLetter = qtyInLetter;
        return this;
    }

    public AuctionBuilder priceForCourier(Double priceForCourier) {
        this.priceForCourier = priceForCourier;
        return this;
    }

    public AuctionBuilder priceForNextItemInCourier(Double priceForNextItemInCourier) {
        this.priceForNextItemInCourier = priceForNextItemInCourier;
        return this;
    }

    public AuctionBuilder qtyInCourier(Integer qtyInCourier) {
        this.qtyInCourier = qtyInCourier;
        return this;
    }

    public AuctionBuilder sendingTime(SendingTime sendingTime) {
        this.sendingTime = sendingTime;
        return this;
    }

    public AuctionBuilder color(Color color) {
        this.color = color;
        return this;
    }

    public List<NewAuctionField> build() {
        List<NewAuctionField> fields = new ArrayList<>();

        fields.add(new NewAuctionField(1, Type.STRING, title));
        fields.add(new NewAuctionField(2, Type.INTEGER, category));
        fields.add(new NewAuctionField(4, Type.INTEGER, duration.getType()));
        fields.add(new NewAuctionField(29, Type.INTEGER, sellType.getType()));
        fields.add(new NewAuctionField(5, Type.INTEGER, qty));
        fields.add(new NewAuctionField(8, Type.FLOAT, price));
        fields.add(new NewAuctionField(9, Type.INTEGER, country));
        fields.add(new NewAuctionField(10, Type.INTEGER, state.getType()));
        fields.add(new NewAuctionField(11, Type.STRING, city));
        fields.add(new NewAuctionField(12, Type.INTEGER, transportPaidBy.getType()));
        fields.add(new NewAuctionField(14, Type.INTEGER, invoice.getType()));
        fields.add(new NewAuctionField(24, Type.STRING, desc));
        fields.add(new NewAuctionField(28, Type.INTEGER, units.getType()));
        fields.add(new NewAuctionField(32, Type.STRING, zip));

        if (priceForLetter != null && priceForNextItemInLetter != null && qtyInLetter != null) {
            fields.add(new NewAuctionField(43, Type.FLOAT, priceForLetter));
            fields.add(new NewAuctionField(143, Type.FLOAT, priceForNextItemInLetter));
            fields.add(new NewAuctionField(243, Type.INTEGER, qtyInLetter));
        }

        if (priceForCourier != null && priceForNextItemInCourier != null && qtyInCourier != null) {
            fields.add(new NewAuctionField(44, Type.FLOAT, priceForCourier));
            fields.add(new NewAuctionField(144, Type.FLOAT, priceForNextItemInCourier));
            fields.add(new NewAuctionField(244, Type.INTEGER, qtyInCourier));
        }

        fields.add(new NewAuctionField(340, Type.INTEGER, sendingTime.getType()));

        if (color != null) {
            new NewAuctionField(3110, Type.INTEGER, color.getType());
        }

        if (image != null) {
            fields.add(new NewAuctionField(16, Type.IMAGE, image));
        }

        return fields;
    }

}