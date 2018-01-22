/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { FeatureMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/feature-my-suffix/feature-my-suffix-delete-dialog.component';
import { FeatureMySuffixService } from '../../../../../../main/webapp/app/entities/feature-my-suffix/feature-my-suffix.service';

describe('Component Tests', () => {

    describe('FeatureMySuffix Management Delete Component', () => {
        let comp: FeatureMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<FeatureMySuffixDeleteDialogComponent>;
        let service: FeatureMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [FeatureMySuffixDeleteDialogComponent],
                providers: [
                    FeatureMySuffixService
                ]
            })
            .overrideTemplate(FeatureMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FeatureMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FeatureMySuffixService);
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
