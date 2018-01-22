/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { BannerMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/banner-my-suffix/banner-my-suffix-delete-dialog.component';
import { BannerMySuffixService } from '../../../../../../main/webapp/app/entities/banner-my-suffix/banner-my-suffix.service';

describe('Component Tests', () => {

    describe('BannerMySuffix Management Delete Component', () => {
        let comp: BannerMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<BannerMySuffixDeleteDialogComponent>;
        let service: BannerMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [BannerMySuffixDeleteDialogComponent],
                providers: [
                    BannerMySuffixService
                ]
            })
            .overrideTemplate(BannerMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BannerMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BannerMySuffixService);
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
