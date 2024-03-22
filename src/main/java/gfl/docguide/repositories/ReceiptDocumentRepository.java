package gfl.docguide.repositories;

import gfl.docguide.data.ReceiptDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptDocumentRepository extends JpaRepository<ReceiptDocument, Long> {
}