/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { InquiryMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/inquiry-my-suffix/inquiry-my-suffix-delete-dialog.component';
import { InquiryMySuffixService } from '../../../../../../main/webapp/app/entities/inquiry-my-suffix/inquiry-my-suffix.service';

describe('Component Tests', () => {

    describe('InquiryMySuffix Management Delete Component', () => {
        let comp: InquiryMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<InquiryMySuffixDeleteDialogComponent>;
        let service: InquiryMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [InquiryMySuffixDeleteDialogComponent],
                providers: [
                    InquiryMySuffixService
                ]
            })
            .overrideTemplate(InquiryMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InquiryMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InquiryMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
