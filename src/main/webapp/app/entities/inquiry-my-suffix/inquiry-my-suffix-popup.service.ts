import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { InquiryMySuffix } from './inquiry-my-suffix.model';
import { InquiryMySuffixService } from './inquiry-my-suffix.service';

@Injectable()
export class InquiryMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private inquiryService: InquiryMySuffixService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.inquiryService.find(id).subscribe((inquiry) => {
                    inquiry.createdDate = this.datePipe
                        .transform(inquiry.createdDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.inquiryModalRef(component, inquiry);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.inquiryModalRef(component, new InquiryMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    inquiryModalRef(component: Component, inquiry: InquiryMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.inquiry = inquiry;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
