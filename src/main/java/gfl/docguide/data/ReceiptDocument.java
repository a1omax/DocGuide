package gfl.docguide.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;


@Setter
@Getter
@Entity
@NoArgsConstructor
public class ReceiptDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Column
    private String docText;

    @Column
    @CreationTimestamp
    private Instant createdOn;

    public ReceiptDocument(String docText){
        this.docText = docText;
    }

}
